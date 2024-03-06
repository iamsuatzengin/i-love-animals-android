package com.suatzengin.iloveanimals.ui.advertisementdetail.adapter.comment

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.suatzengin.iloveanimals.data.model.advertisement.comment.AdCommentApiModel
import com.suatzengin.iloveanimals.data.model.advertisement.comment.isUserOwner
import com.suatzengin.iloveanimals.databinding.ItemAdDetailCommentBinding

class AdDetailCommentViewHolder(
    private val binding: ItemAdDetailCommentBinding,
    private val onItemDeleteClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AdCommentApiModel, currentUserId: String) {
        binding.tvAdvertisementComment.text = item.comment

        binding.tvEditComment.isVisible = item.isUserOwner(currentUserId)
        binding.tvDeleteComment.isVisible = item.isUserOwner(currentUserId)

        binding.tvDeleteComment.setOnClickListener {
            onItemDeleteClick(item.commentId.toString())
        }
    }
}
