package com.suatzengin.iloveanimals.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun updateVisibility() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(fabIsVisible = !uiState.value.fabIsVisible)
            }
        }
    }

    fun updateVisibilityWithAnim(bool: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(visibilityWithAnim = bool)
            }
        }
    }
}
