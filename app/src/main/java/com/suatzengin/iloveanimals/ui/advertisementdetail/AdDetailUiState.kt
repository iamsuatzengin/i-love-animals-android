package com.suatzengin.iloveanimals.ui.advertisementdetail

import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement

data class AdDetailUiState(
    val isLoading: Boolean = false,
    val advertisement: Advertisement? = null,
)