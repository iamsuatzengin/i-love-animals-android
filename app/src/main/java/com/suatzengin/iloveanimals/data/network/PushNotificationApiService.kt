package com.suatzengin.iloveanimals.data.network

import com.suatzengin.iloveanimals.data.network.NetworkConstants.PUSH_NOTIFICATION_DEVICE
import com.suatzengin.iloveanimals.util.extension.apiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.appendPathSegments
import javax.inject.Inject

class PushNotificationApiService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun createPushNotificationDevice(deviceToken: String) = apiCall<Unit> {
        client.post(PUSH_NOTIFICATION_DEVICE) {
            url {
                appendPathSegments(deviceToken)
            }
        }
    }
}
