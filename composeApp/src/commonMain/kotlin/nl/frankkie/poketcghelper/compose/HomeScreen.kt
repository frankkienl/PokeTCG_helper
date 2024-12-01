package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.frankkie.poketcghelper.AppState
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeCardSet
import nl.frankkie.poketcghelper.model.PokeRarity
import nl.frankkie.poketcghelper.model.PokeType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

@Composable
fun HomeScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    homeScreenViewModel: HomeScreenViewModel = viewModel { HomeScreenViewModel() }
) {
    val homeScreenUiState = homeScreenViewModel.uiState.collectAsState().value
    val appState = appViewModel.appState.collectAsState().value

    Scaffold(
        topBar = { HomeScreenTopBar(navController, appViewModel, homeScreenViewModel) },
    ) {
        if (appState.cardSets.isEmpty()) {
            Text("Loading Card Sets...")
        } else {
            GridOfCards(appState, appState.cardSets, homeScreenUiState.cardFilter, onCardClick = { _cardSet, _card ->
                homeScreenViewModel.showCardDialog(_cardSet, _card)
            })
        }
        if (homeScreenUiState.cardDialogData != null) {
            PokeCardDialog(
                cardDialogData = homeScreenUiState.cardDialogData,
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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GridOfCards(
    appState: AppState,
    cardSets: List<PokeCardSet>,
    cardFilter: PokeCardFilter,
    onCardClick: (PokeCardSet, PokeCard) -> Unit) {
    //Placeholder image
    var placeHolderImage by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(cardSets) {
        val bytes = Res.readBytes("files/card_symbols/card_back.png")
        placeHolderImage = bytes.decodeToImageBitmap()
    }

    //Grid of cards
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(5),
    ) {

        cardSets.forEach { cardSet ->
            item(span = { GridItemSpan(5) }) {
                //Cardset logo
                var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
                LaunchedEffect(cardSet) {
                    val bytes = Res.readBytes("files/card_symbols/${cardSet.codeName}/${cardSet.imageUrl}")
                    imageBitmap = bytes.decodeToImageBitmap()
                }
                if (imageBitmap == null) {
                    Text(cardSet.displayName)
                } else {
                    imageBitmap?.let { Image(it, contentDescription = cardSet.displayName, modifier = Modifier.padding(8.dp)) }
                }
            }
            //Cards
            val filteredCards = cardSet.cards.filter { someCard ->
                matchesCardFilter(someCard, cardFilter)
            }
            items(filteredCards) { card ->
                PokeCardComposable(
                    cardSet = cardSet,
                    pokeCard = card,
                    isLoggedIn = appState.myUser!=null,
                    isOwned = true,
                    cardPlaceholderImage = placeHolderImage,
                    onClick = { _set, _card ->
                        onCardClick(_set, _card)
                    }
                )
            }
        }
    }
}

fun matchesCardFilter(card: PokeCard, cardFilter: PokeCardFilter): Boolean {
    //Rarity
    if (cardFilter.rarities.isNotEmpty()) {
        val rarity = PokeRarity.valueOf(card.pokeRarity?: "UNKNOWN")
        if (!cardFilter.rarities.contains(rarity)) {
            return false
        }
    }
    //Types
    if (cardFilter.types.isNotEmpty()) {
        val type = PokeType.valueOf(card.pokeType ?: "UNKNOWN")
        if (!cardFilter.types.contains(type)) {
            return false
        }
    }
    return true
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
            cardDialogData = null,
            filterDialog = false,
            cardFilter = PokeCardFilter()
        )
    )
    val uiState = _uiState.asStateFlow()

    fun showCardDialog(cardSet: PokeCardSet, card: PokeCard) {
        _uiState.value = _uiState.value.copy(
            cardDialogData = CardDialogData(cardSet, card)
        )
    }

    fun hideCardDialog() {
        _uiState.value = _uiState.value.copy(
            cardDialogData = null
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

    fun setCardFilter(pokeCardFilter: PokeCardFilter) {
        _uiState.value = _uiState.value.copy(
            cardFilter = pokeCardFilter,
        )
    }

}

data class HomeScreenUiState(
    val cardDialogData: CardDialogData?,
    val filterDialog: Boolean,
    val cardFilter: PokeCardFilter = PokeCardFilter()
)