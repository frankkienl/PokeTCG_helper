package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        if (homeScreenUiState.showLogoutDialog) {
            LogoutDialog(appViewModel, homeScreenViewModel)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GridOfCards(
    appState: AppState,
    cardSets: List<PokeCardSet>,
    cardFilter: PokeCardFilter,
    onCardClick: (PokeCardSet, PokeCard) -> Unit
) {
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
                    isLoggedIn = appState.supabaseUserInfo != null,
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
        val rarity = PokeRarity.valueOf(card.pokeRarity ?: "UNKNOWN")
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
    val appState = appViewModel.appState.collectAsState().value
    TopAppBar(
        title = { Text("Poke TCG Helper") },
        actions = {
            IconButton(onClick = { homeScreenViewModel.showFilterDialog() }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
            if (appState.supabaseUserInfo == null) {
                //Not logged in (go to login-screen)
                IconButton(onClick = { navController.navigate(Routes.LoginScreen) }) {
                    Icon(Icons.Outlined.AccountCircle, contentDescription = "Login icon")
                }
            } else {
                //Logged in (this is now a logout-button)
                IconButton(onClick = { homeScreenViewModel.showLogoutDialog(true) }) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Logout icon")
                }
            }
        }
    )
}

@Composable
fun LogoutDialog(appViewModel: AppViewModel, homeScreenViewModel: HomeScreenViewModel) {
    Dialog(
        onDismissRequest = { homeScreenViewModel.showLogoutDialog(false) },
    ) {
        val rememberCoroutineScope = rememberCoroutineScope()
        var isLoading by remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Logout?")
                Spacer(Modifier.height(8.dp))
                Text("Currently logged in as: ")
                Text(appViewModel.appState.value.supabaseUserInfo?.email ?: "Anonymous")
                Spacer(Modifier.height(8.dp))
                Row {
                    TextButton(
                        onClick = { homeScreenViewModel.showLogoutDialog(false) },
                        content = { Text("Cancel") }
                    )
                    Spacer(Modifier.width(8.dp))
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        TextButton(
                            onClick = {
                                rememberCoroutineScope.launch {
                                    isLoading = true
                                    appViewModel.logout()
                                    isLoading = false
                                    homeScreenViewModel.showLogoutDialog(false)
                                }
                            },
                            content = { Text("Logout", color = Color.Red) }
                        )
                    }
                }
            }
        }
    }
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

    fun showLogoutDialog(isVisible: Boolean) {
        _uiState.value = _uiState.value.copy(
            showLogoutDialog = isVisible
        )
    }

}

data class HomeScreenUiState(
    val cardDialogData: CardDialogData?,
    val filterDialog: Boolean,
    val cardFilter: PokeCardFilter = PokeCardFilter(),
    val showLogoutDialog: Boolean = false,
)