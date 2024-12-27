package nl.frankkie.poketcghelper

actual fun openInBrowser(url: String) {
    throw NotImplementedError("openInBrowser is not implemented yet")
}

actual fun getCurrentPlatform(): Platform {
    return Platform.Native
}