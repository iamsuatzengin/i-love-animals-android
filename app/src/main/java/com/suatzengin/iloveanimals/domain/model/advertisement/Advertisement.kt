package com.suatzengin.iloveanimals.domain.model.advertisement

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
) {
    val isImageSizeBiggerThan1: Boolean = images.size > 1
    val isImageSizeBiggerThan2: Boolean = images.size > 2
}
