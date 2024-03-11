package com.suatzengin.iloveanimals.ui.advertisement

sealed interface AdvertisementUiEvent {
    data object ShowErrorMessage : AdvertisementUiEvent
}
