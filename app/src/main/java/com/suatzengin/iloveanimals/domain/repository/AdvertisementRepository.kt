package com.suatzengin.iloveanimals.domain.repository

import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import kotlinx.coroutines.flow.Flow

interface AdvertisementRepository {
    fun getAdvertisementList(): Flow<Resource<List<Advertisement>>>

    fun searchAdvertisement(key: String): Flow<Resource<List<Advertisement>>>

    fun getAdvertisementsByCategory(category: AdvertisementCategory): Flow<Resource<List<Advertisement>>>
}
