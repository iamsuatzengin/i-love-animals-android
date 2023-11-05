package com.suatzengin.iloveanimals.domain.model.advertisement

enum class AdvertisementCategory {
    ALL,
    INJURED,
    FEED_SUPPORT,
    ADOPT;

    companion object {
        fun getWithTitle(category: AdvertisementCategory): String = when (category) {
            ALL -> "Tümü"
            INJURED -> "Yaralı"
            FEED_SUPPORT -> "Mama Desteği"
            ADOPT -> "Sahiplendirme"
        }
    }
}
