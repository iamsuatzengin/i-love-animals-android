package com.suatzengin.iloveanimals.data.model.veterinaryclinic

import kotlinx.serialization.Serializable

@Serializable
data class VeterinaryClinicApiModel(
    val id: Long,
    val clinicName: String,
    val doctorName: String,
    val longitude: String,
    val latitude: String,
    val address: String,
    val postalCode: String,
    val openTimes: String,
    val closeTimes: String,
    val isAmbulanceAvailable: Boolean,
    val images: List<String>,
    val phoneNumber: String,
)
