package com.suatzengin.iloveanimals.domain.mapper

import com.suatzengin.iloveanimals.data.model.charityscore.CharityScoreApiModel
import com.suatzengin.iloveanimals.domain.model.charityscore.CharityScore
import javax.inject.Inject

class CharityScoreMapper @Inject constructor() : Mapper<CharityScoreApiModel, CharityScore> {
    override fun map(input: CharityScoreApiModel): CharityScore = CharityScore(
        scoreId = input.id,
        userId = input.userId,
        userFullName = input.userFullName,
        userImageUrl = input.userImageUrl,
        point = input.point
    )

    fun map(input: List<CharityScoreApiModel>) = input.map { map(it) }
}
