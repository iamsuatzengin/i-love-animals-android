package com.suatzengin.iloveanimals.domain.repository

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.auth.LoginApiModel
import com.suatzengin.iloveanimals.data.model.auth.RegisterRequest
import com.suatzengin.iloveanimals.data.network.NetworkResult

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): NetworkResult<LoginApiModel>

    suspend fun register(request: RegisterRequest): NetworkResult<MessageResponse>
}