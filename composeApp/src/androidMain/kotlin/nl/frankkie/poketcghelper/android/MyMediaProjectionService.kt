package nl.frankkie.poketcghelper.android

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.SurfaceTexture
import android.hardware.display.DisplayManager
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.Surface
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.getSystemService
import nl.frankkie.poketcghelper.R


class MyMediaProjectionService : Service() {

    var surfaceTexture: SurfaceTexture? = null

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

        if (ACTION_STOP == intent.action) {
            Log.v(TAG, "Stopping MyMediaProjectionService")
            stopSelf()
            return START_NOT_STICKY
        }

        ServiceCompat.startForeground(
            this,
            NOTIFICATION_ID,
            getMyNotification(),
            ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION
        )

        val resultCode = intent.getIntExtra("resultCode", -1)
        val result = intent.getParcelableExtra("result", Intent::class.java)

        initMediaProjection(resultCode, result!!)

        return START_NOT_STICKY
    }

    fun initMediaProjection(resultCode: Int, result: Intent) {
        try {
            val mediaProjectionManager = getSystemService(MediaProjectionManager::class.java)
            val mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, result)
            if (mediaProjection == null) {
                Log.e(TAG, "MediaProjection is null")
                return
            }

            val handler = Handler(Looper.getMainLooper())
            mediaProjection.registerCallback(object : MediaProjection.Callback() {
                override fun onStop() {
                    super.onStop()
                }
            }, handler)

            surfaceTexture = SurfaceTexture(false)
            val surface = Surface(surfaceTexture)

            mediaProjection.createVirtualDisplay(
                TAG,
                1080,
                2340,
                1,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                surface,
                null,
                null
            )

        } catch (e: Exception) {
            Log.e(TAG, "Error initializing MediaProjection: ${e.message}")
        }
    }

    fun getMyNotification(): Notification {
        createNotificationChannel()

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("PokeTCG")
            .setContentText("Screen recording in progress")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_notification_bell)
            .setOngoing(true)
            .addAction(getStopAction())

        return notificationBuilder.build()
    }

    private fun getStopAction() : NotificationCompat.Action {
        val stopIntent = Intent(this, MyMediaProjectionService::class.java).apply {
            action = ACTION_STOP
        }

        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopAction = NotificationCompat.Action.Builder(
            R.drawable.ic_stop,
            "Stop Recording",
            stopPendingIntent // You can set a PendingIntent to stop the service here
        ).build()

        return stopAction
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel.
        val name = "PokeTCG Foreground Service"
        val descriptionText = "Foreground service for Media Projection (screen recording)"
        val importance = NotificationManager.IMPORTANCE_LOW
        val mChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            name,
            importance
        )
        mChannel.description = descriptionText
        // Register the channel with the system. You can't change the importance
        // or other notification behaviors after this.
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    override fun onDestroy() {
        Log.v(TAG, "MyMediaProjectionService - onDestroy")
        //Release any resources or stop any ongoing tasks
        surfaceTexture?.release()
        this.getSystemService<NotificationManager>()?.cancel(NOTIFICATION_ID)
    }

    companion object {
        private const val TAG = "PokeTCG"
        private const val NOTIFICATION_CHANNEL_ID = "PokeTCG_foreground_service"
        private const val NOTIFICATION_ID = 1
        const val ACTION_STOP = "ACTION_STOP"
    }
}