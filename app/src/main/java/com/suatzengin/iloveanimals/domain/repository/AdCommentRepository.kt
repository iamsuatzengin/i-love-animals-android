package com.suatzengin.iloveanimals.domain.repository

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.advertisement.comment.AdCommentApiModel
import com.suatzengin.iloveanimals.data.model.advertisement.comment.PostCommentRequest
import com.suatzengin.iloveanimals.domain.model.Resource

interface AdCommentRepository {

    suspend fun getAdvertisementComments(advertisementId: String): List<AdCommentApiModel>

    suspend fun postAdvertisementComment(
        advertisementId: String,
        request: PostCommentRequest
    ): Resource<MessageResponse>

    suspend fun deleteAdvertisementComment(commentId: String): Resource<MessageResponse>

    suspend fun updateAdvertisementComment(
        commentId: String,
        request: PostCommentRequest
    ): Resource<MessageResponse>
}
