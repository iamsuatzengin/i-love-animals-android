package com.suatzengin.iloveanimals.data.repository

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.auth.LoginRequest
import com.suatzengin.iloveanimals.data.model.auth.LoginApiModel
import com.suatzengin.iloveanimals.data.model.auth.RegisterRequest
import com.suatzengin.iloveanimals.data.network.AuthService
import com.suatzengin.iloveanimals.data.network.NetworkResult
import com.suatzengin.iloveanimals.di.dispatcher.Dispatcher
import com.suatzengin.iloveanimals.di.dispatcher.IlaDispatchers
import com.suatzengin.iloveanimals.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    @Dispatcher(IlaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): NetworkResult<LoginApiModel> = withContext(ioDispatcher) {
        authService.login(
            LoginRequest(email = email, password = password)
        )
    }

    override suspend fun register(
        request: RegisterRequest
    ): NetworkResult<MessageResponse> = withContext(ioDispatcher) {
        authService.register(request = request)
    }
}
