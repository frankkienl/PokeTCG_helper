package nl.frankkie.poketcghelper.compose.friends

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.frankkie.poketcghelper.AppState
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.compose.homescreen.PokeCardForFriendDetailScreen
import nl.frankkie.poketcghelper.model.OwnedCard
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeExpansion
import nl.frankkie.poketcghelper.supabase.UserOwnedCardRow
import nl.frankkie.poketcghelper.supabase.dbTableUserOwnedCards

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FriendDetailScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    friendUid: String,
    friendEmail: String,
    friendDetailScreenViewModel: FriendDetailScreenViewModel = viewModel { FriendDetailScreenViewModel(friendUid, friendEmail) }
) {
    val appState = appViewModel.appState.collectAsState().value
    if (appState.supabaseUserInfo == null) {
        //User is not logged in
        println("AnalyticsScreen: not logged in, exit screen;")
        navController.popBackStack()
        return
    }
    val coroutineScope = rememberCoroutineScope()
    val uiState = friendDetailScreenViewModel.uiState.collectAsState().value
    LaunchedEffect(null) {
        //Refresh friend details
        val supabaseClient = appState.supabaseClient
        val userUid = appState.supabaseUserInfo.id
        if (supabaseClient == null || userUid == null) {
            println("FriendDetailScreen: supabaseClient is null, exit screen;")
            navController.popBackStack()
            return@LaunchedEffect
        }
        friendDetailScreenViewModel.refreshFriendDetails(supabaseClient, friendUid)
    }
    LaunchedEffect(uiState.friendOwnedCards) {
        friendDetailScreenViewModel.refreshTradeAdvice(appState)
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Poke TCG Helper - Friend detail screen") },
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() },
                    content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") })
            }
        )
    }) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Friend detail screen - $friendEmail")
            Spacer(Modifier.height(16.dp))

            if (uiState.isLoading) {
                Text("Loading friend details...")
            } else {
                if (uiState.errorMessage != null) {
                    Text("Error: ${uiState.errorMessage}")
                } else {
                    Text("Friend UID: ${uiState.friendUid}")
                    Text("Friend email: ${uiState.friendEmail}")
                    Text("Friend owned cards: ${uiState.friendOwnedCards.size}")
                    Spacer(Modifier.height(16.dp))
                    Text("Trade advice; me to friend:")
                    FlowRow {
                        for (card in uiState.tradeAdviceMeToFriend) {
                            val pokeExpansion = findPokeExpansion(appState, card.expansion) ?: return@FlowRow
                            PokeCardForFriendDetailScreen(
                                pokeExpansion = pokeExpansion,
                                pokeCard = card,
                                cardPlaceholderImage = null,
                            )
                        }
                    }
                    Text("Trade advice; friend to me:")
                    FlowRow {
                        for (card in uiState.tradeAdviceFriendToMe) {
                            val pokeExpansion = findPokeExpansion(appState, card.expansion) ?: return@FlowRow
                            PokeCardForFriendDetailScreen(
                                pokeExpansion = pokeExpansion,
                                pokeCard = card,
                                cardPlaceholderImage = null,
                            )
                        }
                    }
                }
            }
        }
    }
}

fun findPokeExpansion(appState: AppState, expansion: String?): PokeExpansion? {
    return appState.pokeExpansions.find { someExpansion -> someExpansion.symbol == expansion }
}

class FriendDetailScreenViewModel(
    val friendUid: String,
    val friend_email: String
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        FriendDetailsScreenState(friendUid, friend_email)
    )
    val uiState = _uiState.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(
            isLoading = isLoading,
            errorMessage = null
        )
    }

    fun setErrorMessage(errorMessage: String?) {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            errorMessage = errorMessage
        )
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }

    fun setFriendOwnedCards(friendOwnedCards: List<UserOwnedCardRow>) {
        _uiState.value = _uiState.value.copy(
            friendOwnedCards = friendOwnedCards,
            isLoading = false
        )
    }

    suspend fun refreshFriendDetails(supabaseClient: SupabaseClient, friendUid: String) {
        println("refreshFriendDetails")
        setLoading(true)
        val db = supabaseClient.postgrest
        val listOfFriendOwnedCards = db.from(dbTableUserOwnedCards).select(columns = Columns.ALL) {
            filter {
                eq("user_uid", friendUid)
            }
        }.decodeList<UserOwnedCardRow>()
        setFriendOwnedCards(listOfFriendOwnedCards)
        println("refreshFriendDetails: ${listOfFriendOwnedCards.size} cards")
    }

    fun refreshTradeAdvice(appState: AppState) {
        println("refreshTradeAdvice")
        val myOwnedCards = appState.ownedCards
        val friendOwnedCards = _uiState.value.friendOwnedCards
        val tradeAdviceMeToFriend = myOwnedCards.filter { myCard ->
            myCard.amount >= 1 && !friendOwnedCards.any { friendCard ->
                friendCard.card_amount >= 1
                        && friendCard.card_set_id == myCard.pokeExpansion.symbol
                        && friendCard.card_number == myCard.pokeCard.number
            }
        }.map { someCard -> findPokeCard(appState, someCard) }
        val tradeAdviceFriendToMe = friendOwnedCards.filter { friendCard ->
            friendCard.card_amount >= 1 && !myOwnedCards.any { myCard ->
                myCard.amount >= 1
                        && myCard.pokeExpansion.symbol == friendCard.card_set_id
                        && myCard.pokeCard.number == friendCard.card_number
            }
        }.map { someCard -> findPokeCard(appState, someCard) }
        _uiState.value = _uiState.value.copy(
            tradeAdviceMeToFriend = tradeAdviceMeToFriend,
            tradeAdviceFriendToMe = tradeAdviceFriendToMe
        )
    }
}

fun findPokeCard(appState: AppState, someCard: UserOwnedCardRow): PokeCard {
    val cardSet = appState.pokeExpansions.find { someCard.card_set_id == it.codeName }
    //remove null safety !!
    return cardSet!!.cards.find { someCard.card_number == it.number }!!
}

fun findPokeCard(appState: AppState, someCard: OwnedCard): PokeCard {
    val cardSet = appState.pokeExpansions.find { someCard.pokeCard.expansion == it.codeName }
    //remove null safety !!
    return cardSet!!.cards.find { someCard.pokeCard.number == it.number }!!
}

data class FriendDetailsScreenState(
    val friendUid: String,
    val friendEmail: String,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val friendOwnedCards: List<UserOwnedCardRow> = emptyList(),
    val tradeAdviceMeToFriend: List<PokeCard> = emptyList(),
    val tradeAdviceFriendToMe: List<PokeCard> = emptyList(),
)