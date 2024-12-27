package nl.frankkie.poketcghelper

import android.content.Intent
import android.net.Uri

actual fun openInBrowser(url: String) {
    throw NotImplementedError("openInBrowser is not implemented yet")
}

actual fun getCurrentPlatform(): Platform {
    return Platform.Android
}