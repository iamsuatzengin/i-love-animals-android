package com.suatzengin.iloveanimals.data.model.advertisement.comment

import kotlinx.serialization.Serializable

@Serializable
data class PostCommentRequest(
    val comment: String,
)
