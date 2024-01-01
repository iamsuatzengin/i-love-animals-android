package com.suatzengin.iloveanimals.ui.createadvertisement

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateAdvertisementUiState(
    val categoryId: Int? = null,
    val title: String = "",
    val description: String = "",
    val imageList: List<Uri> = emptyList(),
    val longitude: String = "",
    val latitude: String = "",
    val address: String = "",
    val postalCode: String = ""
): Parcelable
