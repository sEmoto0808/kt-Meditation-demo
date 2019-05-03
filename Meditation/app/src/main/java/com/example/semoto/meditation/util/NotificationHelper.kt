package com.example.semoto.meditation.util

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import com.example.semoto.meditation.R
import com.example.semoto.meditation.view.main.MainActivity

class NotificationHelper(val context: Context) {

    private val notificationId = NOTIFICATION_ID
    private var manager: NotificationManager? = null

    fun startNotification() {

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, RQ_PENDING_INTENT, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(context.resources.getString(R.string.notification_title))
            .setContentText(context.resources.getString(R.string.notification_text))
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.meiso_logo))
            .setAutoCancel(true)
        builder.setContentIntent(pendingIntent)
        val notification = builder.build()
        notification.flags = Notification.DEFAULT_LIGHTS or Notification.FLAG_AUTO_CANCEL

        manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager?.notify(notificationId, notification)
    }

    fun cancelNotification() {
        manager?.cancel(notificationId)
    }
}