package com.suatzengin.iloveanimals.domain.usecase

import com.suatzengin.iloveanimals.data.local.LocalDataStoreManager
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAdvertisementListUseCase @Inject constructor(
    private val localDataStoreManager: LocalDataStoreManager,
    private val advertisementRepository: AdvertisementRepository
) {
    suspend operator fun invoke(
        newPostalCode: String?,
        canUpdate: Boolean = false
    ): Flow<Resource<List<Advertisement>>> {
        var postalCode = ""

        if (canUpdate && newPostalCode != null) {
            localDataStoreManager.savePostalCode(newPostalCode)
            postalCode = newPostalCode
        }

        postalCode = postalCode.ifEmpty { localDataStoreManager.postalCode.first() }

        if (postalCode.isEmpty()) {
            return advertisementRepository.getAdvertisementList()
        }

        return advertisementRepository.getAdvertisementsByPostalCode(postalCode)
    }
}
