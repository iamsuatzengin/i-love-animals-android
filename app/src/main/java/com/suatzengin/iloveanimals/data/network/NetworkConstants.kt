package com.suatzengin.iloveanimals.data.network

object NetworkConstants {
    const val BASE_URL = "http://192.168.1.108:8080/"

    // Authentication Endpoints
    const val LOGIN = "/login"
    const val REGISTER = "/register"

    // Advertisement
    const val ADVERTISEMENT_LIST = "/advertisement-list"
    const val ADVERTISEMENT_LIST_POSTAL_CODE = "/advertisement-list/postalCode"
    const val ADVERTISEMENT_LIST_FILTER = "/advertisement-list/filter"
    const val ADVERTISEMENT_DETAIL = "/advertisement"
    const val CREATE_ADVERTISEMENT = "/add-advertisement"

    // Advertisement Comment
    const val COMMENT = "/comment"

    // User Advertisement
    const val USER_ADVERTISEMENT = "/user-advertisement"

    // Profile
    const val PROFILE = "/user/"

    // Search
    const val SEARCH_ADVERTISEMENT = "/search"
    const val QUERY_KEY = "key"

    // Veterinary Clinic
    const val VETERINARY_CLINICS = "/clinics"

    // Charity Score
    const val CHARITY_SCORE = "/charity-score"

    // Complete Advertisement
    const val COMPLETE_ADVERTISEMENT = "/complete-advertisement"

    // Push Notification Device
    const val PUSH_NOTIFICATION_DEVICE = "/push-notification-device"
}
