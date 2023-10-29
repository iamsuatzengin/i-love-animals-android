package com.suatzengin.iloveanimals.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            Log.w("AUTH", "$email - $password")

            if(_uiState.value.isValid.not()) {
                _uiEvent.emit(LoginUiEvent.Error(message = "Geçersiz email-şifre"))
                return@launch
            }

            val response = repository.login(email = email, password = password)

            when {
                (response.status && response.token.isNullOrEmpty().not()) -> {
                    _uiEvent.emit(LoginUiEvent.NavigateToHome)
                    Log.w("AUTH", "token: ${response.token}")
                }

                else -> {
                    _uiEvent.emit(LoginUiEvent.Error(message = response.message))
                }
            }
        }
    }

    fun setEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun setPassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun clearUiState() {
        viewModelScope.launch {
            _uiState.emit(LoginUiState())
        }
    }
}

sealed class LoginUiEvent {
    data object NavigateToHome : LoginUiEvent()
    data class Error(val message: String) : LoginUiEvent()
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
) {
    val isValid: Boolean = email.isNotBlank()
            && email.contains("@")
            && password.isNotBlank()
}