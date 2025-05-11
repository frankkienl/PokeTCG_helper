package nl.frankkie.poketcghelper

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource
import poketcg_helper.composeapp.generated.resources.Res
import poketcg_helper.composeapp.generated.resources.ic_app_logo

fun main() = application {
    val windowState = rememberWindowState()
    windowState.size = DpSize(560.dp, 1024.dp)
    windowState.position = WindowPosition.Absolute(0.dp, 0.dp)
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        icon = painterResource(Res.drawable.ic_app_logo),
        title = "PokeTCG_helper",
    ) {
        App()
    }
}