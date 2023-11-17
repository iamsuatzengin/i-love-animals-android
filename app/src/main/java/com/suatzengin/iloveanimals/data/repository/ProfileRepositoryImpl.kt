package com.suatzengin.iloveanimals.data.repository

import com.suatzengin.iloveanimals.data.model.profile.ProfileApiModel
import com.suatzengin.iloveanimals.data.network.ProfileService
import com.suatzengin.iloveanimals.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val service: ProfileService
): ProfileRepository {
    override fun getUserProfile(userId: String): Flow<ProfileApiModel> {
        return service.getUserProfile(userId = userId)
    }
}
