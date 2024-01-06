package com.suatzengin.iloveanimals.data.repository

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.advertisement.CompleteAdvertisementRequest
import com.suatzengin.iloveanimals.data.network.CompleteAdvertisementService
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.repository.CompleteAdRepository
import com.suatzengin.iloveanimals.util.extension.mapOnSuccess
import javax.inject.Inject

class CompleteAdRepositoryImpl @Inject constructor(
    private val service: CompleteAdvertisementService
) : CompleteAdRepository {
    override suspend fun completeAdvertisement(
        requestBody: CompleteAdvertisementRequest
    ): Resource<MessageResponse> = service.completeAdvertisement(requestBody).mapOnSuccess()
}
