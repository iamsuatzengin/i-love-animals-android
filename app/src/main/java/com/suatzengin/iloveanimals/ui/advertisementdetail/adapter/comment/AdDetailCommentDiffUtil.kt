package com.suatzengin.iloveanimals.ui.advertisementdetail.adapter.comment

import androidx.recyclerview.widget.DiffUtil
import com.suatzengin.iloveanimals.data.model.advertisement.comment.AdCommentApiModel

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
