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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.frankkie.poketcghelper.cardSet
import nl.frankkie.poketcghelper.initializeCards
import nl.frankkie.poketcghelper.model.PokeCardSet

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = viewModel { HomeScreenViewModel() }
) {
    var cardsInitialized by remember { mutableStateOf(false) }
    LaunchedEffect(null) {
        if (!cardsInitialized) {
            cardSet = initializeCards()
            cardsInitialized = true
        }
    }

    Scaffold(
        topBar = { HomeScreenTopBar(navController) },
    ) {
        if (!cardsInitialized) {
            Text("Loading...")
        } else {
            GridOfCards(cardSet)
        }
    }
}

@Composable
fun GridOfCards(cardSet: PokeCardSet) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(5),
    ) {
        items(cardSet.cards) { card ->
            PokeCardComposable(
                card,
                isLoggedIn = true,
                isOwned = false,
                onClick = {}
            )
        }
    }
}

@Composable
fun HomeScreenTopBar(navController: NavController) {
    TopAppBar(title = { Text("Poke TCG Helper") })
}

class HomeScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState(false))
    val uiState = _uiState.asStateFlow()
}

data class HomeScreenUiState(
    val isLoggedIn: Boolean
)