package com.suatzengin.iloveanimals.ui.auth.register

sealed interface RegisterUiEvent {
    class Error(val message: String): RegisterUiEvent
    class PasswordNotConfirmed(val message: String): RegisterUiEvent
    data object NavigateToLogin: RegisterUiEvent
}
