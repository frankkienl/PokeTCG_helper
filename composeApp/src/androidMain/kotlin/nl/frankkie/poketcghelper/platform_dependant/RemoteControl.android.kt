package nl.frankkie.poketcghelper.platform_dependant

import android.content.Intent
import nl.frankkie.poketcghelper.android.MyRemoteClientActivity

actual fun openRemoteControlHost() {
    // Not implemented yet
}

actual fun openRemoteControlClient() {
    // Not yet implemented
    appContext?.let { safeContext ->
        val intent = Intent(safeContext, MyRemoteClientActivity::class.java)
        safeContext.startActivity(intent)
    }
}