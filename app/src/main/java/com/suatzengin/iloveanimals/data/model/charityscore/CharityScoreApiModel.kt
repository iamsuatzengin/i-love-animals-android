package com.suatzengin.iloveanimals.data.model.charityscore

import kotlinx.serialization.Serializable

@Serializable
data class CharityScoreApiModel(
    val id: Int,
    val userId: String,
    val userMail: String,
    val userFullName: String,
    val userImageUrl: String,
    val point: Long
)
