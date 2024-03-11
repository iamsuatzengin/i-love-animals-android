package com.suatzengin.iloveanimals.ui.advertisementdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.data.auth.IlaAuthHandler
import com.suatzengin.iloveanimals.data.model.advertisement.comment.PostCommentRequest
import com.suatzengin.iloveanimals.domain.model.onError
import com.suatzengin.iloveanimals.domain.model.onSuccess
import com.suatzengin.iloveanimals.domain.repository.AdCommentRepository
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import com.suatzengin.iloveanimals.util.jwtdecode.JwtDecoder
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
    private val authHandler: IlaAuthHandler
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AdDetailUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val advertisementId: String? = savedStateHandle[ADVERTISEMENT_ID]

    val currentUserId: String
        get() = JwtDecoder.decode(authHandler.accessToken).userId

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

            val resource = advertisementDetailDeferred.await()

            resource.onSuccess { advertisement ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        advertisement = advertisement,
                        comments = commentsDeferred.await()
                    )
                }
            }.onError { message ->
                _uiEvent.emit(AdDetailUiEvent.ShowMessage(message))
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
                _uiEvent.emit(AdDetailUiEvent.ShowMessage("Boş yorum gönderemezsiniz."))
                return@launch
            }

            commentRepository.postAdvertisementComment(
                advertisementId,
                PostCommentRequest(comment)
            ).onSuccess {
                getUpdatedComments()

                _uiEvent.emit(AdDetailUiEvent.Success)
            }.onError { errorMessage ->
                _uiEvent.emit(AdDetailUiEvent.ShowMessage(message = errorMessage))
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
                _uiEvent.emit(AdDetailUiEvent.ShowMessage(message = errorMessage))
            }
        }
    }

    companion object {
        const val ADVERTISEMENT_ID = "advertisementId"
    }
}
