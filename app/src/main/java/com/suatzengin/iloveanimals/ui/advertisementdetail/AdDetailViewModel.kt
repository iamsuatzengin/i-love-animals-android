package com.suatzengin.iloveanimals.ui.advertisementdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val repository: AdvertisementRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val advertisementId: String? = savedStateHandle[ADVERTISEMENT_ID]

        getAdvertisementDetail(advertisementId)
    }

    private fun getAdvertisementDetail(advertisementId: String?) {
        viewModelScope.launch {
            if (advertisementId == null) return@launch

            _uiState.update { it.copy(isLoading = true) }

            when (val result = repository.getAdvertisementDetail(id = advertisementId)) {
                is Resource.Error -> {
                    // TODO("will handle error!")
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, advertisement = result.data) }
                }

                Resource.Loading -> {
                    // no-op
                }
            }
        }
    }

    companion object {
        const val ADVERTISEMENT_ID = "advertisementId"
    }
}