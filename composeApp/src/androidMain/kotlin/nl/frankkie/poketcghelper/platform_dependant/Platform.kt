package nl.frankkie.poketcghelper.platform_dependant

import android.content.Context

actual fun getCurrentPlatform(): Platform {
    return Platform.Android
}

// Set from MainActivity.onCreate
var appContext: Context? = null
