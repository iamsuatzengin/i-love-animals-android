package com.suatzengin.iloveanimals.di

import com.suatzengin.iloveanimals.data.repository.AuthRepositoryImpl
import com.suatzengin.iloveanimals.domain.repository.AuthRepository
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
    ) : AuthRepository
}