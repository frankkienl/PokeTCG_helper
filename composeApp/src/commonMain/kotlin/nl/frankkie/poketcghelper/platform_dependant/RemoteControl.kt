package nl.frankkie.poketcghelper.platform_dependant

expect fun openRemoteControlHost()
expect fun openRemoteControlClient()

fun tryToOpenRemoteControlHost() {
    if (isRemoteControlHostSupported(getCurrentPlatform())) {
        openRemoteControlHost()
    } else {
        println("openRemoteControlHost: Not implemented yet for Platform ${getCurrentPlatform()}")
    }
}

fun tryToOpenRemoteControlClient() {
    if (isRemoteControlClientSupported(getCurrentPlatform())) {
        openRemoteControlClient()
    } else {
        println("openRemoteControlClient: Not implemented yet for Platform ${getCurrentPlatform()}")
    }
}

fun isRemoteControlHostSupported(platform: Platform): Boolean {
    return when (platform) {
        Platform.Desktop -> true
        Platform.WasmJS -> false
        Platform.Native -> false
        Platform.Android -> false
        Platform.Unknown -> false
    }
}

fun isRemoteControlClientSupported(platform: Platform): Boolean {
    return when (platform) {
        Platform.Desktop -> false
        Platform.WasmJS -> false
        Platform.Native -> false
        Platform.Android -> true
        Platform.Unknown -> false
    }
}