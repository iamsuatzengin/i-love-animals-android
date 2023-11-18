package com.suatzengin.iloveanimals.domain.model

import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement

data class UserProfile(
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val profileImageUrl: String,
    val charityScorePoint: Long,
    val bio: String,
    val twitterLink: String,
    val instagramLink: String,
    val userAdvertisementList: List<Advertisement>
) {
    val advertisementPostedCount: Int
        get() {
            return userAdvertisementList.size
        }

    val advertisementPostedCompleted: Int
        get() {
            return userAdvertisementList.filter { it.isCompleted }.size
        }
}
