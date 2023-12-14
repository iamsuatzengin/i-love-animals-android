package com.suatzengin.iloveanimals.ui.advertisementdetail

import com.suatzengin.iloveanimals.data.model.advertisement.comment.AdCommentApiModel
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement

data class AdDetailUiState(
    val isLoading: Boolean = false,
    val advertisement: Advertisement? = null,
    val comments: List<AdCommentApiModel> = emptyList(),
)
