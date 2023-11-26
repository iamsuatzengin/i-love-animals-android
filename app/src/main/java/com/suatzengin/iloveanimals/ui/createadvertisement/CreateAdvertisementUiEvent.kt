package com.suatzengin.iloveanimals.ui.createadvertisement

sealed interface CreateAdvertisementUiEvent {
    data object CreatedAdvertisement : CreateAdvertisementUiEvent

    data class Error(val message: String) : CreateAdvertisementUiEvent
}
