package com.suatzengin.iloveanimals.ui.createadvertisement

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.data.model.advertisement.CreateAdvertisementRequest
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import com.suatzengin.iloveanimals.util.extension.ZERO
import com.suatzengin.iloveanimals.util.extension.or
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAdViewModel @Inject constructor(
    private val advertisementRepository: AdvertisementRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateAdvertisementUiState())
    val uiState = _uiState.asStateFlow()

    fun createAdvertisement(
        title: String,
        description: String,
        address: String,
    ) {
        viewModelScope.launch {
            runCatching {
                val request = CreateAdvertisementRequest(
                    title = title,
                    description = description,
                    category = uiState.value.categoryId or ZERO,
                    images = uiState.value.imageList,
                    longitude = "Longitude",
                    latitude = "Latitude",
                    address = address
                )

                advertisementRepository.createAdvertisement(request)
            }.onFailure {
                Log.e("CreateAdvertisement", "Error", it)
            }
        }
    }

    fun updateImageList(imageList: List<String>) {
        viewModelScope.launch {
            _uiState.update { it.copy(imageList = imageList) }
        }
    }

    fun updateCategory(category: AdvertisementCategory) {
        viewModelScope.launch {
            _uiState.update { it.copy(categoryId = AdvertisementCategory.getCategoryWithId(category)) }
        }
    }
}
