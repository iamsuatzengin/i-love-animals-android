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

    fun createAdvertisement() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val imageUris = uiState.value.imageList

            val result = createAdvertisementUseCase(
                title = uiState.value.title,
                description = uiState.value.description,
                category = uiState.value.categoryId or ZERO,
                longitude = uiState.value.longitude,
                latitude = uiState.value.latitude,
                address = uiState.value.address,
                images = imageUris
            )

            when (result) {
                is Resource.Error -> {
                    Log.i("CreateAdvertisement", "Error: ${result.message}")

                    _uiEvent.emit(CreateAdvertisementUiEvent.Error(result.message))
                }

                is Resource.Success -> {
                    _uiEvent.emit(CreateAdvertisementUiEvent.CreatedAdvertisement)

                    _uiState.update { it.copy(isLoading = false) }
                }

                else -> {
                    // no-op
                }
            }
        }
    }

    fun updateTitle(title: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(title = title) }
        }
    }

    fun updateDescription(description: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(description = description) }
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

    fun updateLocationInformation(
        latitude: String,
        longitude: String,
        address: String
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    latitude = latitude,
                    longitude = longitude,
                    address = address
                )
            }
        }
    }
}
