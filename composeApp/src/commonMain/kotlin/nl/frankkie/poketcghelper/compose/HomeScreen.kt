package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.cardSet
import nl.frankkie.poketcghelper.initializeCards
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeCardSet
import nl.frankkie.poketcghelper.model.PokeCardSetPack

@Composable
fun HomeScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    viewModel: HomeScreenViewModel = viewModel { HomeScreenViewModel() }
) {
    var cardsInitialized by remember { mutableStateOf(false) }
    LaunchedEffect(null) {
        if (!cardsInitialized) {
            cardSet = initializeCards()
            cardsInitialized = true
        }
    }

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = { HomeScreenTopBar(navController, appViewModel) },
    ) {
        if (!cardsInitialized) {
            Text("Loading...")
        } else {
            GridOfCards(cardSet, onCardClick = { _cardSet, _card ->
                viewModel.showDialog(_cardSet, _card)
            })
            if (uiState.cardDialog != null && uiState.cardSet != null) {
                PokeCardDialog(
                    pokeCardSet = uiState.cardSet,
                    pokeCard = uiState.cardDialog,
                    amountOwned = 1,
                    onChangeAmountOwned = {},
                    onDismissRequest = { viewModel.hideDialog() }
                )
            }
        }
    }
}

@Composable
fun GridOfCards(cardSet: PokeCardSet, onCardClick: (PokeCardSet, PokeCard) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(5),
    ) {
        items(cardSet.cards) { card ->
            PokeCardComposable(
                cardSet = cardSet,
                pokeCard = card,
                isLoggedIn = false,
                isOwned = true,
                onClick = { _set, _card ->
                    onCardClick(_set, _card)
                }
            )
        }
    }
}

@Composable
fun HomeScreenTopBar(navController: NavController, appViewModel: AppViewModel) {
    TopAppBar(
        title = { Text("Poke TCG Helper") },
        actions = {
            if (appViewModel.appState.value.myUser != null) {
                IconButton(onClick = { navController.navigate(Routes.LoginScreen) }) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Login icon")
                }
            } else {
                IconButton(onClick = { navController.navigate(Routes.LoginScreen) }) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Logout icon")
                }
            }
        }
    )
}

class HomeScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeScreenUiState(
            isLoggedIn = false,
            cardSet = null,
            cardDialog = null
        )
    )
    val uiState = _uiState.asStateFlow()

    fun showDialog(cardSet: PokeCardSet, card: PokeCard) {
        _uiState.value = _uiState.value.copy(
            cardSet = cardSet,
            cardDialog = card
        )
    }

    fun hideDialog() {
        _uiState.value = _uiState.value.copy(
            cardSet = null,
            cardDialog = null
        )
    }

}

data class HomeScreenUiState(
    val isLoggedIn: Boolean,
    val cardSet: PokeCardSet?,
    val cardDialog: PokeCard?
)