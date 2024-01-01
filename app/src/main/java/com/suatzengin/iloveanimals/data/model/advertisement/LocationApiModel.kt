package com.suatzengin.iloveanimals.data.model.advertisement

import kotlinx.serialization.Serializable

@Serializable
data class LocationApiModel(
    val longitude: String,
    val latitude: String,
    val postalCode: String
)
