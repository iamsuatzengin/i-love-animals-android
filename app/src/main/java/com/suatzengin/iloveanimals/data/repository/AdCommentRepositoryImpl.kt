package com.suatzengin.iloveanimals.data.repository

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.advertisement.comment.AdCommentApiModel
import com.suatzengin.iloveanimals.data.model.advertisement.comment.PostCommentRequest
import com.suatzengin.iloveanimals.data.network.AdvertisementCommentService
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.repository.AdCommentRepository
import com.suatzengin.iloveanimals.util.extension.mapOnSuccess
import javax.inject.Inject

class AdCommentRepositoryImpl @Inject constructor(
    private val service: AdvertisementCommentService
) : AdCommentRepository {

    override suspend fun getAdvertisementComments(advertisementId: String): List<AdCommentApiModel> {
        return runCatching {
            service.getAdvertisementComments(advertisementId)
        }.getOrElse {
            println("comment error: $it")
            emptyList()
        }
    }

    override suspend fun postAdvertisementComment(
        advertisementId: String,
        request: PostCommentRequest
    ): Resource<MessageResponse> {
        return service.postAdvertisementComment(
            advertisementId = advertisementId,
            request = request
        ).mapOnSuccess()
    }
}
