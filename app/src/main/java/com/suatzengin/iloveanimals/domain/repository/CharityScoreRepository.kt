package com.suatzengin.iloveanimals.domain.repository

import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.charityscore.CharityScore
import kotlinx.coroutines.flow.Flow

interface CharityScoreRepository {
    fun getCharityScores(): Flow<Resource<List<CharityScore>>>
}
