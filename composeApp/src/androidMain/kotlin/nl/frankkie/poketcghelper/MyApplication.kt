package nl.frankkie.poketcghelper

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //Create notification channel
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel.
        //Yes, hard codes strings. Fix this later.
        //TODO: Use string resources
        val name = "PokeTCG Foreground Service"
        val descriptionText = "Foreground service for Media Projection (screen recording)"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
        mChannel.description = descriptionText
        // Register the channel with the system. You can't change the importance
        // or other notification behaviors after this.
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "PokeTCG_foreground_service"
    }
}