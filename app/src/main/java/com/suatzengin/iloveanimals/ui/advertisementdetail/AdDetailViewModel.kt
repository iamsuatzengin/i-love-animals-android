package com.suatzengin.iloveanimals.ui.advertisementdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.data.model.advertisement.comment.PostCommentRequest
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.onError
import com.suatzengin.iloveanimals.domain.model.onSuccess
import com.suatzengin.iloveanimals.domain.repository.AdCommentRepository
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val advertisementRepository: AdvertisementRepository,
    private val commentRepository: AdCommentRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AdDetailUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val advertisementId: String? = savedStateHandle[ADVERTISEMENT_ID]

    init {
        getAdvertisementDetail(advertisementId)
    }

    private fun getAdvertisementDetail(advertisementId: String?) {
        viewModelScope.launch {
            if (advertisementId == null) return@launch

            _uiState.update { it.copy(isLoading = true) }

            val advertisementDetailDeferred = async {
                advertisementRepository.getAdvertisementDetail(id = advertisementId)
            }

            val commentsDeferred = async {
                commentRepository.getAdvertisementComments(advertisementId = advertisementId)
            }

            when (val resource = advertisementDetailDeferred.await()) {
                is Resource.Error -> {
                    sendEvent(AdDetailUiEvent.ShowMessage(resource.message))
                }

                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            advertisement = resource.data,
                            comments = commentsDeferred.await()
                        )
                    }
                }

                Resource.Loading -> {
                    // no-op
                }
            }
        }
    }

    private fun getUpdatedComments() {
        viewModelScope.launch {
            if (advertisementId == null) return@launch

            val comments = commentRepository.getAdvertisementComments(advertisementId)

            _uiState.update { it.copy(comments = comments) }
        }
    }

    fun postAdvertisementComment(comment: String) {
        viewModelScope.launch {
            if (advertisementId == null) return@launch

            if (comment.isEmpty()) {
                sendEvent(AdDetailUiEvent.ShowMessage("Boş yorum gönderemezsiniz."))
                return@launch
            }

            commentRepository.postAdvertisementComment(
                advertisementId,
                PostCommentRequest(comment)
            ).onSuccess {
                Log.i("PostCommentEvent", "Response: $it")

                getUpdatedComments()

                sendEvent(AdDetailUiEvent.Success)
            }.onError { errorMessage ->
                Log.i("PostCommentEvent - Error", "Error: $errorMessage")

                sendEvent(AdDetailUiEvent.ShowMessage(message = errorMessage))
            }
        }
    }

    fun deleteAdvertisementComment(commentId: String) {
        viewModelScope.launch {
            commentRepository.deleteAdvertisementComment(
                commentId = commentId
            ).onSuccess {
                getUpdatedComments()
            }.onError { errorMessage ->
                sendEvent(AdDetailUiEvent.ShowMessage(message = errorMessage))
            }
        }
    }

    private fun sendEvent(uiEvent: AdDetailUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(uiEvent)
        }
    }

    companion object {
        const val ADVERTISEMENT_ID = "advertisementId"
    }
}
