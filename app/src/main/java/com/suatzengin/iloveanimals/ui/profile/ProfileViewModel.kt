package com.suatzengin.iloveanimals.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.data.auth.IlaAuthHandler
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.usecase.GetUserProfileUseCase
import com.suatzengin.iloveanimals.util.jwtdecode.JwtDecoder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val authHandler: IlaAuthHandler
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ProfileUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            val userId = JwtDecoder.decode(authHandler.accessToken).userId

            getUserProfileUseCase(userId = userId).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.update { it.copy(error = result.message, isLoading = false) }
                    }

                    Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        Log.i("Profile", "Profile : ${result.data}")
                        _uiState.update { it.copy(profileUiModel = result.data, isLoading = false) }
                    }
                }
            }
        }
    }

    fun userLogout() {
        viewModelScope.launch {
            runCatching {
                authHandler.logout()
                _uiEvent.emit(ProfileUiEvent.Logout)
            }
        }
    }
}
