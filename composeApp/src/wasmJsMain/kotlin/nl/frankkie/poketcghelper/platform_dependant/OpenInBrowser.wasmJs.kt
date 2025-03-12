package nl.frankkie.poketcghelper.platform_dependant

import kotlinx.browser.window

actual fun openInBrowser(url: String) {
    window.open(url, "_blank")?.focus()
}

