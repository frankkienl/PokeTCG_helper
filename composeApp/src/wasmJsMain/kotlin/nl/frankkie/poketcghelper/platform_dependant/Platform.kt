package nl.frankkie.poketcghelper.platform_dependant

actual fun getCurrentPlatform(): Platform {
    return Platform.WasmJS
}
