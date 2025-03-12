package nl.frankkie.poketcghelper.platform_dependant

enum class Platform {
    Desktop,
    Native,
    Android,
    WasmJS,
    Unknown
}

expect fun getCurrentPlatform(): Platform