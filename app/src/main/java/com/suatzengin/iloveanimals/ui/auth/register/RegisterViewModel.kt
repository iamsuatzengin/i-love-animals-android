package com.suatzengin.iloveanimals.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.data.model.auth.RegisterRequest
import com.suatzengin.iloveanimals.data.network.NetworkResult
import com.suatzengin.iloveanimals.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<RegisterUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun register() {
        viewModelScope.launch {
            if(uiState.value.isPasswordConfirmed.not()) {
                _uiEvent.emit(RegisterUiEvent.PasswordNotConfirmed(message = "Şifreler uyuşmuyor!"))
                return@launch
            }

            val requestBody = RegisterRequest(
                fullName = uiState.value.fullName,
                email = uiState.value.email,
                password = uiState.value.password,
                phoneNumber = uiState.value.confirmPassword,
                profileImageUrl = "url - link"
            )

            when (val response = authRepository.register(request = requestBody)) {
                is NetworkResult.Success -> {
                    _uiEvent.emit(RegisterUiEvent.NavigateToLogin)
                }
                is NetworkResult.Error -> {
                    _uiEvent.emit(RegisterUiEvent.Error(message = response.error.message))
                }
                is NetworkResult.Exception -> {
                    _uiEvent.emit(RegisterUiEvent.Error(message = response.message))
                }
            }
        }
    }

    fun setFullName(fullName: String) {
        viewModelScope.launch { _uiState.update { it.copy(fullName = fullName) } }
    }

    fun setEmail(email: String) {
        viewModelScope.launch { _uiState.update { it.copy(email = email) } }
    }

    fun setPassword(password: String) {
        viewModelScope.launch { _uiState.update { it.copy(password = password) } }
    }

    fun setConfirmPassword(confirmPassword: String) {
        viewModelScope.launch { _uiState.update { it.copy(confirmPassword = confirmPassword) } }
    }

    fun setPhoneNumber(phoneNumber: String) {
        viewModelScope.launch { _uiState.update { it.copy(phoneNumber = phoneNumber) } }
    }
}

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

sealed interface RegisterUiEvent {
    class Error(val message: String): RegisterUiEvent
    class PasswordNotConfirmed(val message: String): RegisterUiEvent
    data object NavigateToLogin: RegisterUiEvent
}