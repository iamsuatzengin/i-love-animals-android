package com.suatzengin.iloveanimals.data.network

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.advertisement.comment.AdCommentApiModel
import com.suatzengin.iloveanimals.data.model.advertisement.comment.PostCommentRequest
import com.suatzengin.iloveanimals.data.network.NetworkConstants.ADVERTISEMENT_DETAIL
import com.suatzengin.iloveanimals.data.network.NetworkConstants.COMMENT
import com.suatzengin.iloveanimals.di.dispatcher.Dispatcher
import com.suatzengin.iloveanimals.di.dispatcher.IlaDispatchers
import com.suatzengin.iloveanimals.util.extension.apiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdvertisementCommentService @Inject constructor(
    private val client: HttpClient,
    @Dispatcher(IlaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getAdvertisementComments(advertisementId: String): List<AdCommentApiModel> =
        withContext(ioDispatcher) {
            val commentList = client.get(ADVERTISEMENT_DETAIL) {
                url {
                    appendPathSegments(advertisementId, "comments")
                }
            }

            commentList.body()
        }

    suspend fun postAdvertisementComment(advertisementId: String, request: PostCommentRequest) =
        apiCall<MessageResponse> {
            val response = client.post(ADVERTISEMENT_DETAIL) {
                url {
                    appendPathSegments(advertisementId, "comment")
                }

                contentType(ContentType.Application.Json)

                setBody(request)
            }

            response.body()
        }

    suspend fun deleteAdvertisementComment(commentId: String) = apiCall<MessageResponse> {
        val response = client.delete(COMMENT) {
            url {
                appendPathSegments(commentId)
            }
        }

        response.body()
    }

    suspend fun updateAdvertisementComment(commentId: String, request: PostCommentRequest) = apiCall<MessageResponse> {
        val response = client.put(COMMENT) {
            url {
                appendPathSegments(commentId)
            }

            contentType(ContentType.Application.Json)

            setBody(request)
        }

        response.body()
    }
}
