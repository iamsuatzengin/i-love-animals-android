package com.suatzengin.iloveanimals.domain.model.advertisement

enum class AdvertisementCategory {
    ALL,
    INJURED,
    FEED_SUPPORT,
    ADOPT;

    companion object {
        fun getWithTitle(category: String): AdvertisementCategory = when (category) {
            "Tümü" -> ALL
            "Yaralı" -> INJURED
            "Mama Desteği" -> FEED_SUPPORT
            "Sahiplendirme" -> ADOPT
            else -> ALL
        }
    }
}
