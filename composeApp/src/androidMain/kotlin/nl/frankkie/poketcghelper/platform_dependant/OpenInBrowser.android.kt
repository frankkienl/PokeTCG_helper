package nl.frankkie.poketcghelper.platform_dependant

import android.content.Intent
import android.net.Uri

actual fun openInBrowser(url: String) {
    openInBrowserAndroid(url)
}

fun openInBrowserAndroid(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    appContext?.startActivity(intent)
}