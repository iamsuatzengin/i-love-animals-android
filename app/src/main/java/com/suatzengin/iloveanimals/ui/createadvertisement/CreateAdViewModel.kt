package com.suatzengin.iloveanimals.ui.createadvertisement

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.domain.usecase.CreateAdvertisementUseCase
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
class CreateAdViewModel @Inject constructor(
    private val createAdvertisementUseCase: CreateAdvertisementUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateAdvertisementUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CreateAdvertisementUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun createAdvertisement(
        title: String,
        description: String,
        address: String,
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val imageUris = uiState.value.imageList

            val result = createAdvertisementUseCase(
                title = title,
                description = description,
                category = uiState.value.categoryId or ZERO,
                address = address,
                images = imageUris
            )

            when (result) {
                is Resource.Error -> {
                    Log.i("CreateAdvertisement", "Error: ${result.message}")

                    _uiEvent.emit(CreateAdvertisementUiEvent.Error(result.message))
                }

                Resource.Loading -> {}

                is Resource.Success -> {
                    _uiEvent.emit(CreateAdvertisementUiEvent.CreatedAdvertisement)

                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun updateImageList(imageList: List<Uri>) {
        viewModelScope.launch {
            val images = mutableListOf<Uri>()

            images.addAll(imageList)

            if (uiState.value.imageList.isNotEmpty()) {
                images.addAll(uiState.value.imageList)
            }

            _uiState.update { it.copy(imageList = images.take(5)) }
        }
    }

    fun updateCategory(category: AdvertisementCategory) {
        viewModelScope.launch {
            _uiState.update { it.copy(categoryId = AdvertisementCategory.getCategoryWithId(category)) }
        }
    }
}

sealed interface CreateAdvertisementUiEvent {
    data object CreatedAdvertisement : CreateAdvertisementUiEvent

    data class Error(val message: String) : CreateAdvertisementUiEvent
}
