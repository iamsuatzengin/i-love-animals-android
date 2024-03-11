package com.suatzengin.iloveanimals.domain.repository

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.advertisement.CompleteAdvertisementRequest
import com.suatzengin.iloveanimals.domain.model.Resource

interface CompleteAdRepository {

    suspend fun completeAdvertisement(
        requestBody: CompleteAdvertisementRequest
    ): Resource<MessageResponse>
}
