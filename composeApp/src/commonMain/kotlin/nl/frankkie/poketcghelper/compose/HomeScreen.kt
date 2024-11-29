package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
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

@Composable
fun HomeScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    homeScreenViewModel: HomeScreenViewModel = viewModel { HomeScreenViewModel() }
) {
    var cardsInitialized by remember { mutableStateOf(false) }
    LaunchedEffect(null) {
        if (!cardsInitialized) {
            cardSet = initializeCards()
            cardsInitialized = true
        }
    }

    val homeScreenUiState = homeScreenViewModel.uiState.collectAsState().value

    Scaffold(
        topBar = { HomeScreenTopBar(navController, appViewModel, homeScreenViewModel) },
    ) {
        if (!cardsInitialized) {
            Text("Loading...")
        } else {
            GridOfCards(cardSet, homeScreenUiState.cardFilter, onCardClick = { _cardSet, _card ->
                homeScreenViewModel.showCardDialog(_cardSet, _card)
            })
            if (homeScreenUiState.cardDialog != null && homeScreenUiState.cardSet != null) {
                PokeCardDialog(
                    pokeCardSet = homeScreenUiState.cardSet,
                    pokeCard = homeScreenUiState.cardDialog,
                    amountOwned = 1,
                    onChangeAmountOwned = {},
                    onDismissRequest = { homeScreenViewModel.hideCardDialog() }
                )
            }
            if (homeScreenUiState.filterDialog) {
                PokeFilterDialog(homeScreenViewModel)
            }
        }
    }
}

@Composable
fun GridOfCards(cardSet: PokeCardSet, cardFilter: PokeCardFilter?, onCardClick: (PokeCardSet, PokeCard) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(5),
    ) {
        val filteredCards = cardSet.cards.filter { someCard ->
            matchesCardFilter(someCard, cardFilter)
        }
        items(filteredCards) { card ->
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

fun matchesCardFilter(card: PokeCard, cardFilter: PokeCardFilter?): Boolean {
    if (cardFilter == null) return true
    if (cardFilter.types.isEmpty()) return true
    if (cardFilter.types.contains(card.pokeType)) return true
    /* TODO: Add other filters too */
    return false
}

@Composable
fun HomeScreenTopBar(navController: NavController, appViewModel: AppViewModel, homeScreenViewModel: HomeScreenViewModel) {
    TopAppBar(
        title = { Text("Poke TCG Helper") },
        actions = {
            IconButton(onClick = { homeScreenViewModel.showFilterDialog() }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
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
            cardDialog = null,
            filterDialog = false,
            cardFilter = null
        )
    )
    val uiState = _uiState.asStateFlow()

    fun showCardDialog(cardSet: PokeCardSet, card: PokeCard) {
        _uiState.value = _uiState.value.copy(
            cardSet = cardSet,
            cardDialog = card
        )
    }

    fun hideCardDialog() {
        _uiState.value = _uiState.value.copy(
            cardSet = null,
            cardDialog = null
        )
    }

    fun showFilterDialog() {
        _uiState.value = _uiState.value.copy(
            filterDialog = true
        )
    }

    fun hideFilterDialog() {
        _uiState.value = _uiState.value.copy(
            filterDialog = false
        )
    }

    fun setCardFilter(pokeCardFilter: PokeCardFilter?) {
        _uiState.value = _uiState.value.copy(
            cardFilter = pokeCardFilter,
        )
    }

}

data class HomeScreenUiState(
    val isLoggedIn: Boolean,
    val cardSet: PokeCardSet?,
    val cardDialog: PokeCard?,
    val filterDialog: Boolean,
    val cardFilter: PokeCardFilter?
)