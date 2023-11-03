package com.suatzengin.iloveanimals.util.extension

import com.suatzengin.iloveanimals.data.network.NetworkResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

suspend inline fun <reified T> apiCall(
    block: () -> HttpResponse
): NetworkResult<T> {
    return try {
        val response = block()

        if (response.status.isSuccess()) {
            NetworkResult.Success(data = response.body<T>())
        } else {
            NetworkResult.Error(error = response.body<T>())
        }
    } catch (e: Exception) {
        NetworkResult.Exception(message = e.localizedMessage ?: "Beklenmeyen bir hata oluştu!")
    }
}