package com.suatzengin.iloveanimals.ui.createadvertisement

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAdViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CreateAdvertisementUiState())
    val uiState = _uiState.asStateFlow()

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

            _uiState.update { it.copy(imageList = images.take(MAX_IMAGE_COUNT)) }
        }
    }

    fun updateCategory(category: AdvertisementCategory) {
        viewModelScope.launch {
            _uiState.update { it.copy(categoryId = category.id) }
        }
    }

    companion object {
        const val MAX_IMAGE_COUNT = 5
    }
}
