package com.suatzengin.iloveanimals.domain.model.advertisement

enum class AdvertisementCategory(private val id: Int, private val title: String) {
    ALL(id = 3, "Tümü"),
    INJURED(id = 0, "Yaralı"),
    FEED_SUPPORT(id = 1, "Mama Desteği"),
    ADOPT(id = 2, "Sahiplendirme");

    companion object {

        fun getCategoryWithId(category: AdvertisementCategory): Int = category.id

        fun getTitle(category: AdvertisementCategory): String = category.title

        fun getWithTitle(title: String) : AdvertisementCategory {
            val category = entries.find { category ->
                category.title == title
            } ?: ALL

            return category
        }
    }
}
