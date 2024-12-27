package nl.frankkie.poketcghelper

fun tryToOpenInBrowser(url: String) {
    if (isOpenInBrowserSupported(getCurrentPlatform())) {
        openInBrowser(url)
    } else {
        println("openInBrowser: Not implemented yet for Platform ${getCurrentPlatform()}")
    }
}

expect fun openInBrowser(url: String)

enum class Platform {
    Desktop,
    Native,
    Android,
    WasmJS,
    Unknown
}

fun isOpenInBrowserSupported(platform: Platform): Boolean {
    return when (platform) {
        Platform.Desktop -> true
        Platform.WasmJS -> true
        Platform.Native -> false
        Platform.Android -> false
        Platform.Unknown -> false
    }
}

expect fun getCurrentPlatform(): Platform