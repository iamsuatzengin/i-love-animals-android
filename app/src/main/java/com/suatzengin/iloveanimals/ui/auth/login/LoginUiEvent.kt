package com.suatzengin.iloveanimals.ui.auth.login

sealed interface LoginUiEvent {
    data object NavigateToHome : LoginUiEvent
    data class Error(val message: String) : LoginUiEvent
}
