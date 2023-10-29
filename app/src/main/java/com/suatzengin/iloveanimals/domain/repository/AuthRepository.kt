package com.suatzengin.iloveanimals.domain.repository

import com.suatzengin.iloveanimals.data.model.auth.LoginApiModel

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): LoginApiModel
}