package com.suatzengin.iloveanimals.ui.advertisement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.suatzengin.iloveanimals.databinding.ItemCategoryListBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory

class CategoryAdapter: ListAdapter<AdvertisementCategory, CategoryViewHolder>(CategoryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(
            parent.context
        )

        val binding = ItemCategoryListBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

class CategoryViewHolder(
    private val binding: ItemCategoryListBinding
): ViewHolder(binding.root) {
    fun bind(item: AdvertisementCategory) {
        binding.tvCategoryTitle.text = AdvertisementCategory.getWithTitle(item)
    }
}

class CategoryDiffUtil: DiffUtil.ItemCallback<AdvertisementCategory>() {
    override fun areItemsTheSame(
        oldItem: AdvertisementCategory,
        newItem: AdvertisementCategory
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: AdvertisementCategory,
        newItem: AdvertisementCategory
    ): Boolean {
        return oldItem == newItem
    }

}