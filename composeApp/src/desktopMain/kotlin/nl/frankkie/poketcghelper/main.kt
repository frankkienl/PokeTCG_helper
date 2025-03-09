package nl.frankkie.poketcghelper

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val windowState = rememberWindowState()
    windowState.size = DpSize(1024.dp, 720.dp)
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "PokeTCG_helper",
    ) {
        App()
    }
}