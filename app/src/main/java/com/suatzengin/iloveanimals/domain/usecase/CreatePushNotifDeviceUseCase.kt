package com.suatzengin.iloveanimals.domain.usecase

import com.suatzengin.iloveanimals.data.local.LocalDataStoreManager
import com.suatzengin.iloveanimals.data.network.PushNotificationApiService
import com.suatzengin.iloveanimals.di.dispatcher.Dispatcher
import com.suatzengin.iloveanimals.di.dispatcher.IlaDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreatePushNotifDeviceUseCase @Inject constructor(
    private val localDataStoreManager: LocalDataStoreManager,
    private val pushNotificationApiService: PushNotificationApiService,
    @Dispatcher(IlaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() = withContext(ioDispatcher) {
        val deviceToken = localDataStoreManager.deviceToken

        pushNotificationApiService.createPushNotificationDevice(
            deviceToken = deviceToken
        )
    }
}
