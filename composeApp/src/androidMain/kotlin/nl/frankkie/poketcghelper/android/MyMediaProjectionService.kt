package nl.frankkie.poketcghelper.android

import android.app.Activity
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import nl.frankkie.poketcghelper.MyApplication
import nl.frankkie.poketcghelper.R

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

        if (intent == null) {
            Log.e(TAG, "Intent is null")
            return START_NOT_STICKY
        }

        val mediaProjectionManager = getSystemService(MediaProjectionManager::class.java)

        val resultCode = intent.getIntExtra("resultCode", -1)
        val result = intent.getParcelableExtra<Intent>("result")

        //val mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, result!!)

        ServiceCompat.startForeground(
            this,
            NOTIFICATION_ID,
            getMyNotification(),
            ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION
        )

        val mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, result!!)

        return START_NOT_STICKY
    }

    fun getMyNotification() : Notification {
        val notificationBuilder = NotificationCompat.Builder(this, MyApplication.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("PokeTCG")
            .setContentText("Screen recording in progress")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_launcher_foreground)

        return notificationBuilder.build()
    }

    fun startProjection(activity: Activity) {
        val mediaProjectionManager = getSystemService(MediaProjectionManager::class.java)
        var mediaProjection : MediaProjection? = null

        //val startMediaProjection = registerForAc

    }

    override fun onDestroy() {
        Log.v(TAG, "MyMediaProjectionService - onDestroy")
        //Release any resources or stop any ongoing tasks
    }

    companion object {
        private const val TAG = "PokeTCG"
        private const val NOTIFICATION_ID = 1
    }
}