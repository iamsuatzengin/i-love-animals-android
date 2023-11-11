package com.suatzengin.iloveanimals.domain.model.advertisement

enum class AdvertisementCategory(private val id: Int) {
    ALL(id = 3),
    INJURED(id = 0),
    FEED_SUPPORT(id = 1),
    ADOPT(id = 2);

    companion object {

        fun getCategoryWithId(category: AdvertisementCategory): Int = category.id
    }
}
