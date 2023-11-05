package com.suatzengin.iloveanimals.domain.repository

import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import kotlinx.coroutines.flow.Flow

interface AdvertisementRepository {
    fun getAdvertisementList(): Flow<Resource<List<Advertisement>>>
}
