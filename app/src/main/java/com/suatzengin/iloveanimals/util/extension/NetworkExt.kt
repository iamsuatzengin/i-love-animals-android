package com.suatzengin.iloveanimals.util.extension

import com.suatzengin.iloveanimals.data.network.NetworkResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

suspend inline fun <reified T> apiCall(
    block: suspend () -> HttpResponse
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

inline fun<reified T> apiCallWithFlow(
    crossinline action: suspend () -> HttpResponse
) = flow<NetworkResult<T>>{
    runCatching {
        action().apply {
            val responseBody = body<T>()
            when {
                status.isSuccess() -> emit(NetworkResult.Success(responseBody))
                else -> emit(NetworkResult.Error(responseBody))
            }
        }
    }.onFailure {
        emit(NetworkResult.Exception(message = it.localizedMessage ?: ""))
    }
}.flowOn(Dispatchers.IO)
