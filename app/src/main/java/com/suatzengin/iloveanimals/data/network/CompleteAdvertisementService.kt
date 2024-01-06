package com.suatzengin.iloveanimals.data.network

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.advertisement.CompleteAdvertisementRequest
import com.suatzengin.iloveanimals.data.network.NetworkConstants.COMPLETE_ADVERTISEMENT
import com.suatzengin.iloveanimals.util.extension.apiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class CompleteAdvertisementService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun completeAdvertisement(
        requestBody: CompleteAdvertisementRequest
    ) = apiCall<MessageResponse> {
        client.post(COMPLETE_ADVERTISEMENT) {
            contentType(ContentType.Application.Json)

            setBody(requestBody)
        }
    }
}
