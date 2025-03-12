package nl.frankkie.poketcghelper.platform_dependant

fun tryToOpenInBrowser(url: String) {
    if (isOpenInBrowserSupported(getCurrentPlatform())) {
        openInBrowser(url)
    } else {
        println("openInBrowser: Not implemented yet for Platform ${getCurrentPlatform()}")
    }
}

expect fun openInBrowser(url: String)

fun isOpenInBrowserSupported(platform: Platform): Boolean {
    return when (platform) {
        Platform.Desktop -> true
        Platform.WasmJS -> true
        Platform.Native -> false
        Platform.Android -> true
        Platform.Unknown -> false
    }
}