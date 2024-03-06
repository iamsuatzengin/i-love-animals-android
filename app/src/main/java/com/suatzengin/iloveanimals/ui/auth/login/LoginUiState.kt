package com.suatzengin.iloveanimals.ui.auth.login

import android.util.Patterns

data class LoginUiState(
    val email: String = "",
    val password: String = "",
) {
    val isEmailValid: Boolean =
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
