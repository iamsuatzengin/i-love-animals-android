package com.suatzengin.iloveanimals.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginApiModel(
    val message: String,
    val status: Boolean,
    val token: String? = null
)
