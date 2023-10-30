package com.suatzengin.iloveanimals.data.network

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.auth.LoginApiModel
import com.suatzengin.iloveanimals.data.model.auth.LoginRequest
import com.suatzengin.iloveanimals.data.model.auth.RegisterRequest
import com.suatzengin.iloveanimals.data.network.NetworkConstants.LOGIN
import com.suatzengin.iloveanimals.data.network.NetworkConstants.REGISTER
import com.suatzengin.iloveanimals.util.extension.apiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class AuthService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun login(request: LoginRequest): NetworkResult<LoginApiModel> = apiCall {
        client.post(LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    suspend fun register(request: RegisterRequest): NetworkResult<MessageResponse> = apiCall {
        client.post(REGISTER) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}
