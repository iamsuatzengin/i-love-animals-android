package com.suatzengin.iloveanimals.data.network

import com.suatzengin.iloveanimals.data.model.auth.LoginRequest
import com.suatzengin.iloveanimals.data.model.auth.LoginApiModel
import com.suatzengin.iloveanimals.data.network.NetworkConstants.LOGIN
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class AuthService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun login(request: LoginRequest): LoginApiModel {
        val response = client.post(LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body<LoginApiModel>()
    }
}