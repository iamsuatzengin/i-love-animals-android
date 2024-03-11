package com.suatzengin.iloveanimals.ui.confirmadvertisement

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.onError
import com.suatzengin.iloveanimals.domain.model.onSuccess
import com.suatzengin.iloveanimals.domain.usecase.CreateAdvertisementUseCase
import com.suatzengin.iloveanimals.ui.createadvertisement.CreateAdvertisementUiEvent
import com.suatzengin.iloveanimals.ui.createadvertisement.CreateAdvertisementUiState
import com.suatzengin.iloveanimals.util.extension.ZERO
import com.suatzengin.iloveanimals.util.extension.or
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmAdViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createAdvertisementUseCase: CreateAdvertisementUseCase
) : ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CreateAdvertisementUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val uiState = savedStateHandle.getStateFlow(
        ADVERTISEMENT_ARGS_KEY,
        CreateAdvertisementUiState()
    )

    fun createAdvertisement() {
        viewModelScope.launch {
            _loadingState.update { true }

            val imageUris = uiState.value.imageList

            val result = createAdvertisementUseCase(
                title = uiState.value.title,
                description = uiState.value.description,
                category = uiState.value.categoryId or ZERO,
                longitude = uiState.value.longitude,
                latitude = uiState.value.latitude,
                address = uiState.value.address,
                images = imageUris,
                postalCode = uiState.value.postalCode
            )

            result.onSuccess {
                _uiEvent.emit(CreateAdvertisementUiEvent.CreatedAdvertisement)

                _loadingState.update { false }
            }.onError { message ->
                _loadingState.update { false }
                _uiEvent.emit(CreateAdvertisementUiEvent.Error(message))
            }
        }
    }

    companion object {
        const val ADVERTISEMENT_ARGS_KEY = "confirmAdvertisement"
    }
}
