package com.suatzengin.iloveanimals.data.model.advertisement

import kotlinx.serialization.Serializable

@Serializable
data class CreateAdvertisementRequest(
    val title: String,
    val description: String,
    val category: Int,
    val images: List<String>,
    val longitude: String,
    val latitude: String,
    val address: String,
    val postalCode: String
)
