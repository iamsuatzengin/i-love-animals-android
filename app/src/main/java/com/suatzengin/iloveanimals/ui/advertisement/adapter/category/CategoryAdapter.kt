package com.suatzengin.iloveanimals.ui.advertisement.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.suatzengin.iloveanimals.databinding.ItemCategoryListBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.ui.advertisement.callback.CategoryCallback

class CategoryAdapter(
    private val categoryCallback: CategoryCallback
): ListAdapter<AdvertisementCategory, CategoryViewHolder>(CategoryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemCategoryListBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding, categoryCallback)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
