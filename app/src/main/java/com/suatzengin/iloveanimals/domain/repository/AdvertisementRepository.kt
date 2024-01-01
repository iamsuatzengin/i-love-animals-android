package com.suatzengin.iloveanimals.domain.repository

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.advertisement.AdvertisementApiModel
import com.suatzengin.iloveanimals.data.model.advertisement.CreateAdvertisementRequest
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import kotlinx.coroutines.flow.Flow

interface AdvertisementRepository {
    fun getAdvertisementList(): Flow<Resource<List<Advertisement>>>

    suspend fun getAdvertisementDetail(id: String): Resource<Advertisement>

    fun searchAdvertisement(key: String): Flow<Resource<List<Advertisement>>>

    fun getAdvertisementsByPostalCode(postalCode: String): Flow<Resource<List<Advertisement>>>

    fun getAdvertisementsByCategory(category: AdvertisementCategory, postalCode: String): Flow<Resource<List<Advertisement>>>

    fun getUserAdvertisement(userId: String): Flow<List<AdvertisementApiModel>>

    suspend fun createAdvertisement(requestBody: CreateAdvertisementRequest): Resource<MessageResponse>
}
