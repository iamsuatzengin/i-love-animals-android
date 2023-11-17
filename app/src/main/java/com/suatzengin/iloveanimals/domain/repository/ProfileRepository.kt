package com.suatzengin.iloveanimals.domain.repository

import com.suatzengin.iloveanimals.data.model.profile.ProfileApiModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getUserProfile(userId: String): Flow<ProfileApiModel>
}
