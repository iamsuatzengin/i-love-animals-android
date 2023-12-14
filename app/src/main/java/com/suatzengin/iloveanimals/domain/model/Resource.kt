package com.suatzengin.iloveanimals.domain.model

sealed interface Resource<out T> {
    data object Loading : Resource<Nothing>
    data class Success<S>(val data: S?) : Resource<S>
    data class Error(val message: String) : Resource<Nothing>
}

fun <T> Resource<T>.onSuccess(action: (T?) -> Unit): Resource<T> {
    if (this is Resource.Success) {
        action(data)
    }

    return this
}

fun <T> Resource<T>.onError(action: (String) -> Unit): Resource<T> {
    if (this is Resource.Error) {
        action(message)
    }

    return this
}
