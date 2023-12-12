package com.suatzengin.iloveanimals.ui.advertisementdetail

sealed interface AdDetailUiEvent {
    data object Success: AdDetailUiEvent
    data class ShowMessage(val message: String) : AdDetailUiEvent
}
