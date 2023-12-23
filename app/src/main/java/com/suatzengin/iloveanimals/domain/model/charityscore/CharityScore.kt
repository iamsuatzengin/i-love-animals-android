package com.suatzengin.iloveanimals.domain.model.charityscore

data class CharityScore(
    val scoreId: Int,
    val userId: String,
    val userFullName: String,
    val userImageUrl: String,
    val point: Long
)
