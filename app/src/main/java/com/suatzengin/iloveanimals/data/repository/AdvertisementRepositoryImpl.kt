package com.suatzengin.iloveanimals.data.repository

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.advertisement.AdvertisementApiModel
import com.suatzengin.iloveanimals.data.model.advertisement.CreateAdvertisementRequest
import com.suatzengin.iloveanimals.data.network.AdvertisementService
import com.suatzengin.iloveanimals.di.dispatcher.Dispatcher
import com.suatzengin.iloveanimals.di.dispatcher.IlaDispatchers
import com.suatzengin.iloveanimals.domain.mapper.AdvertisementMapper
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import com.suatzengin.iloveanimals.util.extension.mapOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AdvertisementRepositoryImpl @Inject constructor(
    private val service: AdvertisementService,
    private val mapper: AdvertisementMapper,
    @Dispatcher(IlaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : AdvertisementRepository {
    override fun getAdvertisementList(): Flow<Resource<List<Advertisement>>> {
        return service.getAdvertisementList().mapOnSuccess {
            mapper.map(it)
        }.flowOn(ioDispatcher)
    }

    override suspend fun getAdvertisementDetail(id: String): Resource<Advertisement> {
        return service.getAdvertisementDetail(id = id).mapOnSuccess(mapper)
    }

    override fun searchAdvertisement(key: String): Flow<Resource<List<Advertisement>>> {
        return service.searchAdvertisement(key).mapOnSuccess { searchedList ->
            mapper.map(input = searchedList)
        }.flowOn(ioDispatcher)
    }

    override fun getAdvertisementsByCategory(category: AdvertisementCategory): Flow<Resource<List<Advertisement>>> {
        return service.getAdvertisementsByCategory(category).mapOnSuccess { list ->
            mapper.map(input = list)
        }.flowOn(ioDispatcher)
    }

    override fun getUserAdvertisement(userId: String): Flow<List<AdvertisementApiModel>> {
        return service.getUserAdvertisement(userId = userId)
    }

    override suspend fun createAdvertisement(
        requestBody: CreateAdvertisementRequest
    ): Resource<MessageResponse> {
        return service.createAdvertisement(requestBody).mapOnSuccess()
    }
}
