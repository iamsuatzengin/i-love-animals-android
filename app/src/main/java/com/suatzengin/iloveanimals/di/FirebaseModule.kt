package com.suatzengin.iloveanimals.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FirebaseModule {

    @Provides
    @ViewModelScoped
    fun provideFirebaseStorage() = Firebase.storage
}
