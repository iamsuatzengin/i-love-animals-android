package com.suatzengin.iloveanimals.data.repository

import com.suatzengin.iloveanimals.data.network.AdvertisementService
import com.suatzengin.iloveanimals.di.dispatcher.Dispatcher
import com.suatzengin.iloveanimals.di.dispatcher.IlaDispatchers
import com.suatzengin.iloveanimals.domain.mapper.AdvertisementMapper
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
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
}
