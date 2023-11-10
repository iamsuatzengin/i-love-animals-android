package com.suatzengin.iloveanimals.ui.search

import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement

data class SearchUiState(
    val list: List<Advertisement>? = null,
    val isLoading: Boolean = false,
    val message: String = "",
)
