package com.watermelon0guy.busstationalarm.util.location
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.media.SoundPool
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.example.busstationalarm.R
import com.google.android.gms.location.LocationServices
import com.watermelon0guy.busstationalarm.data.LocationPoint
import com.watermelon0guy.busstationalarm.data.LocationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@AndroidEntryPoint
class LocationService: Service() {
    @Inject
    lateinit var repository: LocationRepository
    lateinit var locationPoints: Flow<List<LocationPoint>>

    lateinit var vibr: Vibrator
    lateinit var alarm: Ringtone

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationPoints = repository.getLocationPoints()

        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        alarm = RingtoneManager.getRingtone(applicationContext, alarmUri)
        vibr = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("${getString(R.string.tracking_warning)}...")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.bus_icon)
            .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val chosenPoint = runBlocking { locationPoints.first().find { it.isChosen } }
        locationClient
            .getLocationUpdates(10000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val lat = location.latitude.toString()
                val long = location.longitude.toString()
                val results = FloatArray(3)
                Location.distanceBetween(lat.toDouble(), long.toDouble(), chosenPoint!!.latitude, chosenPoint!!.longitude, results)

                if (results[0] < 300) {
                    alarm.play()
                    delay(3000)
                    vibr.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE))
                    repository.insertLocationPoint(chosenPoint.copy(isChosen = false))
                    alarm.stop()
                    stop()
                }

                val updatedNotification = notification.setContentText(
                    "${getString(R.string.location)}: ($lat, $long)\n${getString(R.string.distance)}: ${results[0].toInt()}"
                )
                notificationManager.notify(1, updatedNotification.build())
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(Service.STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}