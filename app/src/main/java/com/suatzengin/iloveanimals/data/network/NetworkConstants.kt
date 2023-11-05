package com.suatzengin.iloveanimals.data.network

object NetworkConstants {
    const val BASE_URL = "http://192.168.1.40:8080/openapi"

    // Authentication Endpoints
    const val LOGIN = "/login"
    const val REGISTER = "/register"

    // Advertisement
    const val ADVERTISEMENT_LIST = "/advertisement-list"
    const val ADVERTISEMENT_DETAIL = "/advertisement/{id}"
}