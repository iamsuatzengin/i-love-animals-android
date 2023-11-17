package com.suatzengin.iloveanimals.data.network

object NetworkConstants {
    const val BASE_URL = "http://192.168.1.42:8080/"

    // Authentication Endpoints
    const val LOGIN = "/login"
    const val REGISTER = "/register"

    // Advertisement
    const val ADVERTISEMENT_LIST = "/advertisement-list"
    const val ADVERTISEMENT_DETAIL = "/advertisement/{id}"

    // User Advertisement
    const val USER_ADVERTISEMENT = "/user-advertisement"

    // Profile
    const val PROFILE = "/user/"

    // Search
    const val SEARCH_ADVERTISEMENT = "/search"
    const val QUERY_KEY = "key"
}
