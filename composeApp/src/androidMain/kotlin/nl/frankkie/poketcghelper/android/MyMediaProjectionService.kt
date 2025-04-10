package nl.frankkie.poketcghelper.android

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.IBinder
import android.util.Log

class MyMediaProjectionService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null // no binding
    }

    override fun onCreate() {
        super.onCreate()
        Log.v(TAG, "MyMediaProjectionService - onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v(TAG, "MyMediaProjectionService - onStartCommand; Intent: $intent; Flags: $flags; StartId: $startId")
        //return super.onStartCommand(intent, flags, startId)

        startForeground(
            NOTIFICATION_ID,
            getMyNotification(),
            ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION
        )

        return START_NOT_STICKY
    }

    fun getMyNotification() : Notification {
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("PokeTCG")
            .setContentText("Screen recording in progress")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        return notificationBuilder.build()
    }

    fun startProjection(activity: Activity) {
        val mediaProjectionManager = getSystemService(MediaProjectionManager::class.java)
        var mediaProjection : MediaProjection? = null

        val startMediaProjection = registerForAc

    }

    override fun onDestroy() {
        Log.v(TAG, "MyMediaProjectionService - onDestroy")
        //Release any resources or stop any ongoing tasks
    }

    companion object {
        private const val TAG = "PokeTCG"
        private const val REQUEST_CODE = 1000
        private const val NOTIFICATION_CHANNEL_ID = "PokeTCG"
        private const val NOTIFICATION_CHANNEL_NAME = "PokeTCG"
        private const val NOTIFICATION_ID = 1
    }
}