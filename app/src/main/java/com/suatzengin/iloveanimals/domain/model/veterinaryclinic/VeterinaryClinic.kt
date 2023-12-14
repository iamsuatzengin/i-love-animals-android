package com.suatzengin.iloveanimals.domain.model.veterinaryclinic

data class VeterinaryClinic(
    val clinicName: String,
    val doctorName: String,
    val openTimes: String,
    val closeTimes: String,
    val isAmbulanceAvailable: Boolean,
    val images: List<String>,
    val address: Address
)

data class Address (
    val longitude: String,
    val latitude: String,
    val address: String,
    val postalCode: String,
)
