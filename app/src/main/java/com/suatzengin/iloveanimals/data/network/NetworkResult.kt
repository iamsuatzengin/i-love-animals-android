package com.suatzengin.iloveanimals.data.network


sealed interface NetworkResult<T> {
    class Success<T>(val data: T) : NetworkResult<T>
    class Error<T>(val error: T) : NetworkResult<T>
    class Exception<T>(val message: String) : NetworkResult<T>
}