package com.suatzengin.iloveanimals.ui.advertisement.adapter.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.suatzengin.iloveanimals.core.ui.MarginItemDecoration
import com.suatzengin.iloveanimals.databinding.ItemAdCategoryBinding
import com.suatzengin.iloveanimals.ui.advertisement.adapter.category.CategoryAdapter
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.CategoryRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.callback.CategoryCallback

class CategoriesViewHolder(
    private val binding: ItemAdCategoryBinding,
    private val callback: CategoryCallback
): ViewHolder(binding.root) {
    fun bind(item: CategoryRecyclerItem) {
        val recyclerView = binding.rvCategories
        val layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = CategoryAdapter(categoryCallback = callback)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(MarginItemDecoration(
            top = 4, bottom = 4, left = 4, right = 4
        ))
        recyclerView.adapter = adapter

        adapter.submitList(item.categories)
    }
}
