package com.suatzengin.iloveanimals.data.network

sealed interface NetworkResult<T> {
    class Success<T>(val data: T) : NetworkResult<T>
    class Error<E>(val error: E) : NetworkResult<E>
    class Exception<T>(val message: String) : NetworkResult<T>
}
