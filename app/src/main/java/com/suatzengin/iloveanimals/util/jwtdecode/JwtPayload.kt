package com.suatzengin.iloveanimals.util.jwtdecode

import kotlinx.serialization.Serializable

@Serializable
data class JwtPayload(
    val aud: String,
    val iss: String,
    val userId: String,
)
