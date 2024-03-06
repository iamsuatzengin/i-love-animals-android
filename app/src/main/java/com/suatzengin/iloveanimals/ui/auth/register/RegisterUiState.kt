package com.suatzengin.iloveanimals.ui.auth.register

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val phoneNumber: String = "",
) {
    val isPasswordConfirmed = password.isNotBlank()
            && confirmPassword.isNotBlank()
            && password == confirmPassword
}
