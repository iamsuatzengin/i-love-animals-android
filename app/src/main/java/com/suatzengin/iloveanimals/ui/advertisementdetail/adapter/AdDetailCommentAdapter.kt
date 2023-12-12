package com.suatzengin.iloveanimals.ui.advertisementdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suatzengin.iloveanimals.data.model.advertisement.comment.AdCommentApiModel
import com.suatzengin.iloveanimals.data.model.advertisement.comment.isUserOwner
import com.suatzengin.iloveanimals.databinding.ItemAdDetailCommentBinding

class AdDetailCommentAdapter(
    private val currentUserId: String
) :
    ListAdapter<AdCommentApiModel, AdDetailCommentViewHolder>(AdDetailCommentDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdDetailCommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAdDetailCommentBinding.inflate(layoutInflater, parent, false)

        return AdDetailCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdDetailCommentViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item = item, currentUserId = currentUserId)
    }

}

class AdDetailCommentDiffUtil : DiffUtil.ItemCallback<AdCommentApiModel>() {
    override fun areItemsTheSame(
        oldItem: AdCommentApiModel,
        newItem: AdCommentApiModel
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: AdCommentApiModel,
        newItem: AdCommentApiModel
    ): Boolean = oldItem == newItem
}

class AdDetailCommentViewHolder(
    private val binding: ItemAdDetailCommentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AdCommentApiModel, currentUserId: String) {
        binding.tvAdvertisementComment.text = item.comment

        binding.tvEditComment.isVisible = item.isUserOwner(currentUserId)
        binding.tvDeleteComment.isVisible = item.isUserOwner(currentUserId)
    }
}