package com.suatzengin.iloveanimals.domain.mapper

import com.suatzengin.iloveanimals.data.model.advertisement.AdvertisementApiModel
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.domain.model.advertisement.Location
import javax.inject.Inject

class AdvertisementMapper @Inject constructor() : Mapper<AdvertisementApiModel, Advertisement> {
    override fun map(input: AdvertisementApiModel): Advertisement {
        return Advertisement(
            id = input.id,
            creatorId = input.creatorId,
            title = input.title,
            description = input.description,
            category = AdvertisementCategory.getCategoryWithId(input.category),
            images = input.images,
            location = Location(
                longitude = input.location.longitude,
                latitude = input.location.latitude,
                address = input.address,
                postalCode = input.location.postalCode
            ),
            isCompleted = input.isCompleted,
            createdAt = input.createdAt
        )
    }

    fun map(input: List<AdvertisementApiModel>) = input.map { map(it) }
}
