package com.suatzengin.iloveanimals.di.dispatcher

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @Dispatcher(IlaDispatchers.IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(IlaDispatchers.Main)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(IlaDispatchers.Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}