package com.suatzengin.iloveanimals.ui.createadvertisement

import android.net.Uri

data class CreateAdvertisementUiState(
    val categoryId: Int? = null,
    val imageList: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
)
