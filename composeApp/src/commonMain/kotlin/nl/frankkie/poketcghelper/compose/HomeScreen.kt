package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import nl.frankkie.poketcghelper.cardSet
import nl.frankkie.poketcghelper.initializeCards

@Composable
fun HomeScreen(navController: NavController) {
    var cardsInitialized by remember { mutableStateOf(false) }
    LaunchedEffect(null) {
        if (!cardsInitialized) {
            cardSet = initializeCards()
            cardsInitialized = true
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Poke TCG Helper") }) },
    ) {
        if (!cardsInitialized) {
            Text("Loading...")
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(5),
            ) {
                items(cardSet.cards) { card ->
                    PokeCardComposable(card)
                }
            }
        }
    }
}