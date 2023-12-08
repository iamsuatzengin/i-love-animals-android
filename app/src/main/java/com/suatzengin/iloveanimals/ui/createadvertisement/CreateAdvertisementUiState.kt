package com.suatzengin.iloveanimals.ui.createadvertisement

import android.net.Uri

data class CreateAdvertisementUiState(
    val categoryId: Int? = null,
    val title: String = "",
    val description: String = "",
    val imageList: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val longitude: String = "",
    val latitude: String = "",
    val address: String = "",
)
