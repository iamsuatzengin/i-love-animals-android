package com.suatzengin.iloveanimals.domain.model.advertisement

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Advertisement(
    val id: String,
    val creatorId: String,
    val title: String,
    val description: String,
    val category: AdvertisementCategory,
    val images: List<String>,
    val location: Location,
    val isCompleted: Boolean,
    val createdAt: String
) : Parcelable {

    @IgnoredOnParcel
    val isImageSizeBiggerThan1: Boolean = images.size > 1

    @IgnoredOnParcel
    val isImageSizeBiggerThan2: Boolean = images.size > 2
}
