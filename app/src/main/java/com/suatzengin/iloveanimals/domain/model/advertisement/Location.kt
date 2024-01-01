package com.suatzengin.iloveanimals.domain.model.advertisement

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val longitude: String,
    val latitude: String,
    val address: String,
    val postalCode: String
) : Parcelable
