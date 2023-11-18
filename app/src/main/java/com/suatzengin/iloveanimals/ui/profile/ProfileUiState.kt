package com.suatzengin.iloveanimals.ui.profile

import com.suatzengin.iloveanimals.domain.model.UserProfile

data class ProfileUiState(
    val profileUiModel: UserProfile? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
