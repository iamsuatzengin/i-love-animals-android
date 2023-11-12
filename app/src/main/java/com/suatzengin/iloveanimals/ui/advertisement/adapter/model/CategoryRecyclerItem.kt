package com.suatzengin.iloveanimals.ui.advertisement.adapter.model

import android.content.res.Resources
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory

data class CategoryRecyclerItem(
    val selectedCategory: AdvertisementCategory = AdvertisementCategory.ALL
) : RecyclerItem {
    override val type: Int = 2

    fun setState(
        resource: Resources,
        textView: TextView,
        category: AdvertisementCategory
    ) {
        if (selectedCategory == category) {
            textView.background =
                ResourcesCompat.getDrawable(resource, R.drawable.bg_item_category_selected, null)
        } else {
            textView.background =
                ResourcesCompat.getDrawable(resource, R.drawable.bg_item_category_not_selected, null)
        }
    }
}
