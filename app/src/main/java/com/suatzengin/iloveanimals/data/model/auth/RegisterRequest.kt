package com.suatzengin.iloveanimals.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val profileImageUrl: String,
)