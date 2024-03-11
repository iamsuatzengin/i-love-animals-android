package com.suatzengin.iloveanimals.util.extension

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.suatzengin.iloveanimals.R

private const val CHANNEL_ID = "ChannelId"
private const val CHANNEL_NAME = "HayvanlariSeviyorum"
private const val NOTIFICATION_ID = 1

fun Context.showNotification(
    title: String,
    body: String
) {
    val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(body)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    val notificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val channel = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        description = "Channel Description"
    }

    notificationManager.createNotificationChannel(channel)

    notificationManager.notify(NOTIFICATION_ID, builder.build())
}
