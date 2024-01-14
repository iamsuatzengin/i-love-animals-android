package com.suatzengin.iloveanimals.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.suatzengin.iloveanimals.data.local.LocalDataStoreManager
import com.suatzengin.iloveanimals.util.extension.showNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class PushNotificationService: FirebaseMessagingService() {

    @Inject
    lateinit var localDataStoreManager: LocalDataStoreManager

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.notification?.let { notification ->
            showNotification(
                title = notification.title.orEmpty(),
                body = notification.body.orEmpty()
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        runBlocking {
            localDataStoreManager.saveDeviceToken(deviceToken = token)
        }
    }
}
