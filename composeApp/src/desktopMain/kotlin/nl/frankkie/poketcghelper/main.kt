package nl.frankkie.poketcghelper

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val windowState = rememberWindowState()
    windowState.size = DpSize(560.dp, 1024.dp)
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "PokeTCG_helper",
    ) {
        App()
    }
}