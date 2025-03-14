package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.compose.*
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeExpansion

@Composable
fun HomeScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    homeScreenViewModel: HomeScreenViewModel = viewModel { HomeScreenViewModel() }
) {
    val rememberCoroutineScope = rememberCoroutineScope()
    val homeScreenUiState = homeScreenViewModel.uiState.collectAsState().value
    val appState = appViewModel.appState.collectAsState().value
    var cardAmountLoading by remember { mutableStateOf(false) }
    var filteredCards by remember { mutableStateOf<Map<PokeExpansion, List<PokeCard>>>(emptyMap()) }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { HomeScreenTopBar(navController, appViewModel, homeScreenViewModel, scaffoldState.drawerState) },
        drawerContent = { HomeDrawerContent(navController, appViewModel) },
    ) {
        if (appState.pokeExpansions.isEmpty()) {
            Text("Loading Card Sets...")
        } else {
            val cardSets = appState.pokeExpansions
            val cardFilter = homeScreenUiState.cardFilter
            val tempMap = mutableMapOf<PokeExpansion, List<PokeCard>>()
            cardSets.forEach { cardSet ->
                val cards = cardSet.cards.filter { someCard ->
                    matchesCardFilter(someCard, cardSet, cardFilter, appState)
                }
                tempMap[cardSet] = cards
            }
            filteredCards = tempMap.toMap()

            GridOfCards(
                appState,
                homeScreenViewModel,
                filteredCards,
                cardAmountLoading = cardAmountLoading,
                onCardClick = { _cardSet, _card ->
                    homeScreenViewModel.showCardDialog(_cardSet, _card)
                },
                onCardLongClick = { _cardSet, _card ->
                    navController.navigate(Routes.CardEditScreen(_cardSet.codeName, _card.number))
                },
                onChangeAmountOwned = { _cardSet, _card, _amount ->
                    rememberCoroutineScope.launch {
                        cardAmountLoading = true
                        appViewModel.changeOwnedCardAmount(_cardSet, _card, _amount)
                        cardAmountLoading = false
                    }
                }
            )
        }
        if (homeScreenUiState.cardDialogData != null) {
            val theCardData = homeScreenUiState.cardDialogData
            val amountOwned = appState.ownedCards.find { (it.pokeCard == theCardData.pokeCard && it.pokeExpansion.codeName == theCardData.pokeExpansion.codeName) }?.amount ?: 0
            PokeCardDialog2(
                cardDialogData = homeScreenUiState.cardDialogData,
                amountOwned = amountOwned,
                isAmountLoading = cardAmountLoading,
                isLoggedIn = appState.supabaseUserInfo != null,
                onChangeAmountOwned = { amount ->
                    rememberCoroutineScope.launch {
                        cardAmountLoading = true
                        appViewModel.changeOwnedCardAmount(theCardData.pokeExpansion, theCardData.pokeCard, amount)
                        cardAmountLoading = false
                    }
                },
                onDismissRequest = { homeScreenViewModel.hideCardDialog() }
            )
        }
        if (homeScreenUiState.filterDialog) {
            PokeFilterDialog(homeScreenViewModel, appState)
        }
        if (homeScreenUiState.showLogoutDialog) {
            HomeScreenLogoutDialog(appViewModel, homeScreenViewModel)
        }
    }
}

@Composable
fun HomeScreenTopBar(
    navController: NavController,
    appViewModel: AppViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    drawerState: DrawerState
) {
    val appState = appViewModel.appState.collectAsState().value
    val homeScreenUiState = homeScreenViewModel.uiState.collectAsState().value
    val rememberCoroutineScope = rememberCoroutineScope()
    TopAppBar(
        title = { Text("Poke TCG Helper") },
        navigationIcon = {
            IconButton(onClick = {
                rememberCoroutineScope.launch {
                    if (drawerState.isClosed) {
                        drawerState.open()
                    } else {
                        drawerState.close()
                    }
                }
            }) {
                Icon(Icons.Default.Menu, "Menu")
            }
        },
        actions = {
            IconButton(onClick = { rememberCoroutineScope.launch { appViewModel.refreshOwnedCards() } }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
            }
            if (appState.supabaseUserInfo != null) {
                //Amount mode only available when logged in
                if (homeScreenUiState.amountInputMode) {
                    IconButton(onClick = { homeScreenViewModel.setAmountInputMode(false) }) {
                        Icon(Icons.Filled.Add, contentDescription = "Number input mode")
                    }
                } else {
                    IconButton(onClick = { homeScreenViewModel.setAmountInputMode(true) }) {
                        Icon(Icons.Outlined.Add, contentDescription = "Number input mode")
                    }
                }
            }
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

class HomeScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeScreenUiState()
    )
    val uiState = _uiState.asStateFlow()

    fun showCardDialog(cardSet: PokeExpansion, card: PokeCard) {
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

    fun setAmountInputMode(newValue: Boolean) {
        _uiState.value = _uiState.value.copy(
            amountInputMode = newValue
        )
    }

}

data class HomeScreenUiState(
    val cardDialogData: CardDialogData? = null,
    val filterDialog: Boolean = false,
    val cardFilter: PokeCardFilter = PokeCardFilter(),
    val showLogoutDialog: Boolean = false,
    val amountInputMode: Boolean = false,
)