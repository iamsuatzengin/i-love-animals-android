package com.suatzengin.iloveanimals.ui.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(emptyList<Uri>())
    val uiState = _uiState.asStateFlow()

    fun updateImageList(imageUri: Uri) {
        viewModelScope.launch {
            val newList = uiState.value + imageUri

            _uiState.update { newList }
        }
    }

    fun deleteImage(imageUri: Uri) {
        viewModelScope.launch {
            val newList = uiState.value - imageUri

            _uiState.update { newList }
        }
    }
}
