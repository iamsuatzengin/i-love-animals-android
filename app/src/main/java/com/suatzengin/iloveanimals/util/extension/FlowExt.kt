package com.suatzengin.iloveanimals.util.extension

import com.suatzengin.iloveanimals.data.network.NetworkResult
import com.suatzengin.iloveanimals.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T, R> Flow<NetworkResult<T>>.mapOnSuccess(
    action: (T) -> R
): Flow<Resource<R>>  {
    return map { networkResult ->
        when (networkResult) {
            is NetworkResult.Error -> Resource.Error(networkResult.error.toString())
            is NetworkResult.Exception -> Resource.Error(networkResult.message)
            is NetworkResult.Success -> Resource.Success(action(networkResult.data))
        }
    }.onStart { emit(Resource.Loading) }
}

fun <T>NetworkResult<T>.mapOnSuccess() : Resource<T> {
    return when (this) {
        is NetworkResult.Error -> Resource.Error(this.error.toString())
        is NetworkResult.Exception -> Resource.Error(this.message)
        is NetworkResult.Success -> Resource.Success(this.data)
    }
}
