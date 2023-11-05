package com.suatzengin.iloveanimals.data.network

import com.suatzengin.iloveanimals.data.model.advertisement.AdvertisementApiModel
import com.suatzengin.iloveanimals.data.network.NetworkConstants.ADVERTISEMENT_LIST
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdvertisementService @Inject constructor(
    private val client: HttpClient
) {
    fun getAdvertisementList() = apiCall<List<AdvertisementApiModel>>{
        client.get(ADVERTISEMENT_LIST)
    }
}

inline fun<reified T> apiCall(
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
}
