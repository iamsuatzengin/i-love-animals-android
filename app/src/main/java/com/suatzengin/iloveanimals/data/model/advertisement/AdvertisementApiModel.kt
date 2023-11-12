package com.suatzengin.iloveanimals.data.model.advertisement

import kotlinx.serialization.Serializable

@Serializable
data class AdvertisementApiModel(
    val id: String,
    val creatorId: String,
    val title: String,
    val description: String,
    val category: Int,
    val images: List<String>,
    val location: LocationApiModel,
    val address: String,
    val isCompleted: Boolean,
    val createdAt: String
)
