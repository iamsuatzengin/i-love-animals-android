package com.suatzengin.iloveanimals.ui.advertisementdetail.adapter.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.suatzengin.iloveanimals.data.model.advertisement.comment.AdCommentApiModel
import com.suatzengin.iloveanimals.databinding.ItemAdDetailCommentBinding

class AdDetailCommentAdapter(
    private val currentUserId: String,
    private val onItemDeleteClick: (String) -> Unit
) :
    ListAdapter<AdCommentApiModel, AdDetailCommentViewHolder>(AdDetailCommentDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdDetailCommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAdDetailCommentBinding.inflate(layoutInflater, parent, false)

        return AdDetailCommentViewHolder(
            binding = binding,
            onItemDeleteClick = onItemDeleteClick
        )
    }

    override fun onBindViewHolder(holder: AdDetailCommentViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item = item, currentUserId = currentUserId)
    }
}
