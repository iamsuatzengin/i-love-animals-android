package com.suatzengin.iloveanimals.ui.charityscore

import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.domain.model.charityscore.CharityScore

data class CharityScoreUiState(
    val scores: List<CharityScore> = emptyList(),
    val currentUserScore: CharityScore? = null
) {
    val currentUserRank: Int
        get() = scores.indexOf(currentUserScore).plus(1)
}

fun CharityScoreUiState.getFirstThreeRankIcon(index: Int): Int? = when(index + 1) {
    1 -> R.drawable.ic_first_medal
    2 -> R.drawable.ic_second_medal
    3 -> R.drawable.ic_third_medal
    else -> null
}
