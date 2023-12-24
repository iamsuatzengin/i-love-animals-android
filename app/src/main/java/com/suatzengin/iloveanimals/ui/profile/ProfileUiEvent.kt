package com.suatzengin.iloveanimals.ui.profile

sealed interface ProfileUiEvent {
    data object Logout: ProfileUiEvent
}
