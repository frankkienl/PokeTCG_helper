package nl.frankkie.poketcghelper

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import nl.frankkie.poketcghelper.compose.createNavGraph
import nl.frankkie.poketcghelper.model.PokeCardSet
import org.jetbrains.compose.ui.tooling.preview.Preview

lateinit var cardSet: PokeCardSet

@Composable
@Preview
fun App(navController: NavHostController = rememberNavController()) {
    var isLoggedIn by remember { mutableStateOf(false) }

    MaterialTheme {
        createNavGraph(navController)
    }
}

