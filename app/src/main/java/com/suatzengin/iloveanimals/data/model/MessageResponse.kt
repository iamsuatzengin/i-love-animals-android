package com.suatzengin.iloveanimals.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val message: String,
    val status: Boolean
)
