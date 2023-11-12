package com.suatzengin.iloveanimals.domain.model

sealed interface Resource<out T>{
    data object Loading: Resource<Nothing>
    data class Success<S>(val data: S?): Resource<S>
    data class Error(val message: String): Resource<Nothing>
}
