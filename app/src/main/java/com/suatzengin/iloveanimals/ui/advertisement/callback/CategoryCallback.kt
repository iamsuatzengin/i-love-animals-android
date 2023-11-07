package com.suatzengin.iloveanimals.ui.advertisement.callback

import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory

interface CategoryCallback {
    fun onCategoryClick(category: AdvertisementCategory)
}
