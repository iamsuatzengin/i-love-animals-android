package com.suatzengin.iloveanimals.data.model.advertisement

import kotlinx.serialization.Serializable

@Serializable
data class CompleteAdvertisementRequest(
    val advertisementId: String,
    val creatorId: String
)
