package com.suatzengin.iloveanimals.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.data.auth.IlaAuthHandler
import com.suatzengin.iloveanimals.data.network.NetworkResult
import com.suatzengin.iloveanimals.domain.repository.AuthRepository
import com.suatzengin.iloveanimals.domain.usecase.CreatePushNotifDeviceUseCase
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
    private val repository: AuthRepository,
    private val authHandler: IlaAuthHandler,
    private val createPushNotifDeviceUseCase: CreatePushNotifDeviceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun login() {
        viewModelScope.launch {
            if (uiState.value.isEmailValid.not()) {
                _uiEvent.emit(LoginUiEvent.Error(message = "Geçersiz email girişi!"))
                return@launch
            }

            when (
                val response =
                    repository.login(email = uiState.value.email, password = uiState.value.password)
            ) {
                is NetworkResult.Success -> {
                    authHandler.saveJWT(token = response.data.token.orEmpty())

                    createPushNotifDeviceUseCase()

                    _uiEvent.emit(LoginUiEvent.NavigateToHome)
                }

                is NetworkResult.Error -> {
                    _uiEvent.emit(LoginUiEvent.Error(message = response.error.message))
                }

                is NetworkResult.Exception -> {
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
}
