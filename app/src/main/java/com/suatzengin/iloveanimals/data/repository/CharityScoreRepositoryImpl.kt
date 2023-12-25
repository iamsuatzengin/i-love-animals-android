package com.suatzengin.iloveanimals.data.repository

import com.suatzengin.iloveanimals.data.network.CharityScoreService
import com.suatzengin.iloveanimals.domain.mapper.CharityScoreMapper
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.charityscore.CharityScore
import com.suatzengin.iloveanimals.domain.repository.CharityScoreRepository
import com.suatzengin.iloveanimals.util.extension.mapOnSuccess
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharityScoreRepositoryImpl @Inject constructor(
    private val service: CharityScoreService,
    private val mapper: CharityScoreMapper
) : CharityScoreRepository {
    override fun getCharityScores(): Flow<Resource<List<CharityScore>>> {
        return service.getCharityScores().mapOnSuccess { charityScores ->
            mapper.map(charityScores)
        }
    }
}
