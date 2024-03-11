package com.suatzengin.iloveanimals.ui.helpanimals

import android.net.Uri

data class HelpAnimalUiState(
    val images: List<Uri> = emptyList(),
    val responseText: String = "",
    val isLoading: Boolean = false
)
