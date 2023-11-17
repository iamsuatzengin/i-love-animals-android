package com.suatzengin.iloveanimals.data.model.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileApiModel(
    val profileId: Int = 0,
    val userId: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val profileImageUrl: String? = null,
    val charityScorePoint: Long = 0,
    val bio: String? = null,
    val twitterLink: String? = null,
    val instagramLink: String? = null,
)
