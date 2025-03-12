package nl.frankkie.poketcghelper.platform_dependant

import java.awt.Desktop
import java.net.URI

actual fun openInBrowser(url: String) {
    val uri = URI.create(url)
    // https://stackoverflow.com/a/68426773
    val osName by lazy(LazyThreadSafetyMode.NONE) { System.getProperty("os.name").lowercase() }
    val desktop = Desktop.getDesktop()
    when {
        Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE) -> desktop.browse(uri)
        "mac" in osName -> Runtime.getRuntime().exec("open $uri")
        "nix" in osName || "nux" in osName -> Runtime.getRuntime().exec("xdg-open $uri")
        else -> throw RuntimeException("cannot open $uri")
    }
}
