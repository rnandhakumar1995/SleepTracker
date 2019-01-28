package com.interview.usersleepinginterval

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import androidx.core.app.NotificationCompat
import android.content.IntentFilter
import com.interview.usersleepinginterval.view.MainActivity


lateinit var userSleepingBroadCastReceiver: UserSleepingBroadCastReceiver

class SleepTrackerService : Service() {
    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_USER_PRESENT)
        userSleepingBroadCastReceiver = UserSleepingBroadCastReceiver()
        registerReceiver(userSleepingBroadCastReceiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Sleeper")
            .setContentText("Have a great day! :)")
            .setContentIntent(pendingIntent).build()
        startForeground(1337, notification)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(userSleepingBroadCastReceiver)
    }

    override fun onBind(intent: Intent?) = null

}