package com.suatzengin.iloveanimals.ui.advertisement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.suatzengin.iloveanimals.databinding.ItemAdCategoryBinding
import com.suatzengin.iloveanimals.databinding.ItemAdvertisementBinding
import com.suatzengin.iloveanimals.databinding.ItemTitleLabelBinding
import com.suatzengin.iloveanimals.databinding.ItemTopBinding
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.AdRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.CategoryRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.RecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.TitleRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.TopRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.viewholder.AdvertisementViewHolder
import com.suatzengin.iloveanimals.ui.advertisement.adapter.viewholder.CategoriesViewHolder
import com.suatzengin.iloveanimals.ui.advertisement.adapter.viewholder.TitleViewHolder
import com.suatzengin.iloveanimals.ui.advertisement.adapter.viewholder.TopViewHolder
import com.suatzengin.iloveanimals.ui.advertisement.callback.AdvertisementCallback
import com.suatzengin.iloveanimals.ui.advertisement.callback.CategoryCallback
import com.suatzengin.iloveanimals.ui.advertisement.callback.TopViewCallback

class AdvertisementAdapter(
    private val topViewCallback: TopViewCallback,
    private val advertisementCallback: AdvertisementCallback,
    private val categoryCallback: CategoryCallback
) : ListAdapter<RecyclerItem, ViewHolder>(AdvertisementDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            0 -> TopViewHolder(
                binding = ItemTopBinding.inflate(inflater, parent, false),
                callback = topViewCallback
            )

            1 -> TitleViewHolder(
                ItemTitleLabelBinding.inflate(inflater, parent, false)
            )

            2 -> CategoriesViewHolder(
                binding = ItemAdCategoryBinding.inflate(inflater, parent, false),
                callback = categoryCallback
            )

            else -> AdvertisementViewHolder(
                binding = ItemAdvertisementBinding.inflate(inflater, parent, false),
                callback = advertisementCallback
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is TopRecyclerItem -> {
                (holder as TopViewHolder).bind()
            }

            is TitleRecyclerItem -> (holder as TitleViewHolder).bind(item)
            is CategoryRecyclerItem -> (holder as CategoriesViewHolder).bind()
            is AdRecyclerItem -> (holder as AdvertisementViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }
}
