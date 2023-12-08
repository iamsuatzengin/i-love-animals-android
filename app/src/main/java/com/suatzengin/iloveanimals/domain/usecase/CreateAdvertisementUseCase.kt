package com.suatzengin.iloveanimals.domain.usecase

import android.net.Uri
import android.util.Log
import com.suatzengin.iloveanimals.data.firebase.ImageStorage
import com.suatzengin.iloveanimals.data.firebase.ImageUploadingException
import com.suatzengin.iloveanimals.data.model.advertisement.CreateAdvertisementRequest
import com.suatzengin.iloveanimals.di.dispatcher.Dispatcher
import com.suatzengin.iloveanimals.di.dispatcher.IlaDispatchers
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateAdvertisementUseCase @Inject constructor(
    private val advertisementRepository: AdvertisementRepository,
    private val imageStorage: ImageStorage,
    @Dispatcher(IlaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        title: String,
        description: String,
        category: Int,
        longitude: String,
        latitude: String,
        address: String,
        images: List<Uri>,
    ) = withContext(ioDispatcher) {

        runCatching {
            val uploadedImages = async { imageStorage.uploadImage(images) }

            val request = CreateAdvertisementRequest(
                title = title,
                description = description,
                category = category,
                images = uploadedImages.await(),
                longitude = longitude,
                latitude = latitude,
                address = address
            )

            advertisementRepository.createAdvertisement(request)
        }.onFailure {
            Log.e("CreateAdvertisement", "Error", it)

            if (it is ImageUploadingException) {
                Resource.Error(it.message ?: "Resim yüklenirken bir hata oluştu!")
            }
        }.getOrElse {
            Resource.Error(it.message ?: "Resim yüklenirken bir hata oluştu!")
        }
    }
}
