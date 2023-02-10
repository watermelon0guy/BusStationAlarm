package com.watermelon0guy.busstationalarm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BusStationAlarmApp: Application() {

    companion object {
        lateinit var instance: Application
        lateinit var resourses: Resources
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}