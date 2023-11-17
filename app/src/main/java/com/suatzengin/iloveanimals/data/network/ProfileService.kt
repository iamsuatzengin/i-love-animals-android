package com.suatzengin.iloveanimals.data.network

import com.suatzengin.iloveanimals.data.model.profile.ProfileApiModel
import com.suatzengin.iloveanimals.data.network.NetworkConstants.PROFILE
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileService @Inject constructor(
    private val client: HttpClient
) {
    fun getUserProfile(userId: String) = flow<ProfileApiModel> {
        val response = client.get(PROFILE) {
            url {
                appendPathSegments(userId)
            }
        }

        emit(response.body())
    }
}
