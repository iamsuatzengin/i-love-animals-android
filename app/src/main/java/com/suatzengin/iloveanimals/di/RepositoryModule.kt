package com.suatzengin.iloveanimals.di

import com.suatzengin.iloveanimals.data.repository.AdCommentRepositoryImpl
import com.suatzengin.iloveanimals.data.repository.AdvertisementRepositoryImpl
import com.suatzengin.iloveanimals.data.repository.AuthRepositoryImpl
import com.suatzengin.iloveanimals.data.repository.CharityScoreRepositoryImpl
import com.suatzengin.iloveanimals.data.repository.CompleteAdRepositoryImpl
import com.suatzengin.iloveanimals.data.repository.ProfileRepositoryImpl
import com.suatzengin.iloveanimals.data.repository.VeterinaryClinicRepositoryImpl
import com.suatzengin.iloveanimals.domain.repository.AdCommentRepository
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import com.suatzengin.iloveanimals.domain.repository.AuthRepository
import com.suatzengin.iloveanimals.domain.repository.CharityScoreRepository
import com.suatzengin.iloveanimals.domain.repository.CompleteAdRepository
import com.suatzengin.iloveanimals.domain.repository.ProfileRepository
import com.suatzengin.iloveanimals.domain.repository.VeterinaryClinicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindAdvertisementRepository(
        advertisementRepositoryImpl: AdvertisementRepositoryImpl
    ): AdvertisementRepository

    @Binds
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    abstract fun bindAdvertisementCommentRepository(
        adCommentRepositoryImpl: AdCommentRepositoryImpl
    ): AdCommentRepository

    @Binds
    abstract fun bindVeterinaryClinicRepository(
        veterinaryClinicRepositoryImpl: VeterinaryClinicRepositoryImpl
    ): VeterinaryClinicRepository

    @Binds
    abstract fun bindCharityScoreRepository(
        charityScoreRepositoryImpl: CharityScoreRepositoryImpl
    ): CharityScoreRepository

    @Binds
    abstract fun bindCompleteAdRepository(
        completeAdRepositoryImpl: CompleteAdRepositoryImpl
    ): CompleteAdRepository
}
