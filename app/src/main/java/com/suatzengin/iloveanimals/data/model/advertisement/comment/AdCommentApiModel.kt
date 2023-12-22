package com.suatzengin.iloveanimals.data.model.advertisement.comment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class AdCommentApiModel(
    val commentId: Long,
    val comment: String,
    val advertisementId: String,
    val userId: String? = null,
): Parcelable

fun AdCommentApiModel.isUserOwner(currentUserId: String): Boolean {
    if(userId == null) return false

    return userId == currentUserId
}
