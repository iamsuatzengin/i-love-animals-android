package com.suatzengin.iloveanimals.data.network

import com.suatzengin.iloveanimals.data.model.charityscore.CharityScoreApiModel
import com.suatzengin.iloveanimals.data.network.NetworkConstants.CHARITY_SCORE
import com.suatzengin.iloveanimals.util.extension.apiCallWithFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharityScoreService @Inject constructor(
    private val client: HttpClient
) {

    fun getCharityScores(): Flow<NetworkResult<List<CharityScoreApiModel>>> = apiCallWithFlow {
        client.get(CHARITY_SCORE)
    }
}
