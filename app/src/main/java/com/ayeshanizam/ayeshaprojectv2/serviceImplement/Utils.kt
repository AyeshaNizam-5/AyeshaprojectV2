package com.ayeshanizam.ayeshaprojectv2.serviceImplement


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build

import androidx.core.app.NotificationCompat
import com.ayeshanizam.ayeshaprojectv2.MainActivity
import com.ayeshanizam.ayeshaprojectv2.R


class Utils {
    companion object {
        const val NOTIFICATION_ID = 22
        const val NOTIFICATION_CHANNEL_ID = "notify"

        fun sendNotification(context: Context, msg: String?) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "My Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.description = "Channel description"
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
                channel.enableVibration(false)
                notificationManager.createNotificationChannel(channel)
            }

            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra("notificationID", NOTIFICATION_ID)
                putExtra("msg", msg)
            }

            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification Title")
                .setContentText(msg)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}
