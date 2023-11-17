package com.suatzengin.iloveanimals.domain.usecase

import com.suatzengin.iloveanimals.di.dispatcher.Dispatcher
import com.suatzengin.iloveanimals.di.dispatcher.IlaDispatchers
import com.suatzengin.iloveanimals.domain.mapper.AdvertisementMapper
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.UserProfile
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import com.suatzengin.iloveanimals.domain.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementMapper: AdvertisementMapper,
    @Dispatcher(IlaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(userId: String): Flow<Resource<UserProfile>> {
        return combine(
            profileRepository.getUserProfile(userId),
            advertisementRepository.getUserAdvertisement(userId)
        ) { profileApiModel, userAdvertisementList ->

            val userProfile = UserProfile(
                fullName = profileApiModel.fullName.orEmpty(),
                email = profileApiModel.email.orEmpty(),
                phoneNumber = profileApiModel.phoneNumber.orEmpty(),
                profileImageUrl = profileApiModel.profileImageUrl.orEmpty(),
                charityScorePoint = profileApiModel.charityScorePoint,
                bio = profileApiModel.bio.orEmpty(),
                twitterLink = profileApiModel.twitterLink.orEmpty(),
                instagramLink = profileApiModel.instagramLink.orEmpty(),
                userAdvertisementList = advertisementMapper.map(userAdvertisementList)
            )

            Resource.Success(data = userProfile) as Resource<UserProfile>
        }.catch { throwable ->
            emit(Resource.Error(message = throwable.message ?: "Bir hatayla karşılaşıldı"))
        }.onStart {
            emit(Resource.Loading)
        }.flowOn(ioDispatcher)
    }
}
