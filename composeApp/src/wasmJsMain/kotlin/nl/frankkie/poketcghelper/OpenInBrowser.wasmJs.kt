package nl.frankkie.poketcghelper

import kotlinx.browser.window

actual fun openInBrowser(url: String) {
    window.open(url,"_blank")?.focus()
}

actual fun getCurrentPlatform(): Platform {
    return Platform.WasmJS
}