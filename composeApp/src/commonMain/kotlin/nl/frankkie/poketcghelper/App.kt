package nl.frankkie.poketcghelper

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import nl.frankkie.poketcghelper.compose.createNavGraph
import nl.frankkie.poketcghelper.model.OwnedCard
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeCardSet
import nl.frankkie.poketcghelper.supabase.UserOwnedCardRow
import nl.frankkie.poketcghelper.supabase.dbTableUserOwnedCards
import nl.frankkie.poketcghelper.supabase.supabaseApiKey
import nl.frankkie.poketcghelper.supabase.supabaseUrl
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController(),
    appViewModel: AppViewModel = viewModel { AppViewModel() }
) {
    val myCoroutineScope = rememberCoroutineScope()
    LaunchedEffect(null) {
        // init Supabase
        val supabase = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseApiKey
        ) {
            defaultSerializer = KotlinXSerializer(Json { ignoreUnknownKeys = true })
            install(Auth)
            install(Postgrest)
        }
        appViewModel.setSupabaseClient(supabase)

        //Start listening to auth event
        supabase.auth.sessionStatus.collect { sessionStatus ->
            when (sessionStatus) {
                is SessionStatus.Authenticated -> {
                    println("Supabase: Authenticated")
                    appViewModel.setSupabaseUserInfo(sessionStatus.session.user)
                    println("Supabase: ${sessionStatus.session.user}")
                    //refresh owned cards
                    myCoroutineScope.launch {
                        appViewModel.refreshOwnedCards()
                    }
                }

                is SessionStatus.Initializing -> {
                    println("Supabase: Initializing")
                }

                is SessionStatus.NotAuthenticated -> {
                    println("Supabase: NotAuthenticated")
                    appViewModel.setSupabaseUserInfo(null)
                }

                is SessionStatus.RefreshFailure -> {
                    println("Supabase: RefreshFailure")
                }
            }
        }
    }

    LaunchedEffect(null) {
        if (appViewModel.appState.value.cardSets.isEmpty()) {
            val cardSets = initializeCards()
            appViewModel.setCardSets(cardSets)
        }
    }

    val appState = appViewModel.appState.collectAsState().value
    MaterialTheme {
        createNavGraph(navController, appViewModel, appState)
    }
}

class AppViewModel : ViewModel() {
    private val _appState = MutableStateFlow(
        AppState()
    )
    val appState = _appState.asStateFlow()


    fun setCardSets(cardSets: List<PokeCardSet>) {
        _appState.value = _appState.value.copy(
            cardSets = cardSets
        )
    }

    fun setSupabaseClient(supabaseClient: SupabaseClient) {
        _appState.value = _appState.value.copy(
            supabaseClient = supabaseClient
        )
    }

    fun setSupabaseUserInfo(userInfo: UserInfo?) {
        _appState.value = _appState.value.copy(
            supabaseUserInfo = userInfo
        )
    }

    suspend fun logout() {
        _appState.value.supabaseClient?.auth?.signOut()
        setSupabaseUserInfo(null)
    }

    suspend fun refreshOwnedCards() {
        if (_appState.value.supabaseClient == null) {
            return
        }
        if (_appState.value.supabaseUserInfo == null) {
            return
        }
        if (_appState.value.cardSets.isEmpty()) {
            return
        }
        val db = _appState.value.supabaseClient?.postgrest ?: return
        val listOfOwnedCards = db.from(dbTableUserOwnedCards).select().decodeList<UserOwnedCardRow>()
        val ownedCards = listOfOwnedCards.map { dbRow ->
            val cardSet = _appState.value.cardSets.find { dbRow.card_set_id == it.codeName }
            //remove null safety !!
            val card = cardSet!!.cards.find { dbRow.card_number == it.number }!!
            OwnedCard(
                pokeCardSet = cardSet,
                pokeCard = card,
                amount = dbRow.card_amount,
                remarks = dbRow.card_remarks.toList()
            )

        }
        _appState.value = _appState.value.copy(
            ownedCards = ownedCards
        )
    }

    suspend fun changeOwnedCardAmount(pokeCardSet: PokeCardSet, pokeCard: PokeCard, amount: Int) {
        if (_appState.value.supabaseClient == null) {
            return
        }
        if (_appState.value.supabaseUserInfo == null) {
            return
        }
        if (_appState.value.cardSets.isEmpty()) {
            return
        }
        val db = _appState.value.supabaseClient?.postgrest ?: return
        val userId = _appState.value.supabaseUserInfo?.id?: ""
        val newRow = UserOwnedCardRow(
            id = null,
            user_uid = userId,
            created_at = Clock.System.now(),
            card_set_id = pokeCardSet.codeName,
            card_number = pokeCard.number,
            card_amount = amount,
            card_remarks = emptyArray()
        )
        var oldRow: UserOwnedCardRow? = null
        try {
            oldRow = db.from(dbTableUserOwnedCards).select {
                filter {
                    eq("user_uid", userId)
                    eq("card_set_id", pokeCardSet.codeName)
                    eq("card_number", pokeCard.number)
                }
            }.decodeSingle()
        } catch (e: Exception) {
            //Row does not exist
            println("changeOwnedCardAmount: oldRow")
            println("Error: ${e.message}")
        }
        if (oldRow == null) {
            try {
                db.from(dbTableUserOwnedCards).insert(newRow)
            } catch (e: Exception) {
                println("changeOwnedCardAmount: DB error")
                println("Error: ${e.message}")
                e.printStackTrace()
            }
        } else {
            newRow.id = oldRow.id
            try {
                db.from(dbTableUserOwnedCards).update({
                    set("card_amount", amount)
                }) {
                    filter {
                        eq("id", oldRow.id!!)
//                        eq("user_uid", userId)
//                        eq("card_set_id", pokeCardSet.codeName)
//                        eq("card_number", pokeCard.number)
                    }
                }
            } catch (e: Exception) {
                println("changeOwnedCardAmount: DB error")
                println("Error: ${e.message}")
                e.printStackTrace()
            }
        }
        //
        refreshOwnedCards()
    }
}

data class AppState(
    val cardSets: List<PokeCardSet> = emptyList(),
    val supabaseClient: SupabaseClient? = null,
    val supabaseUserInfo: UserInfo? = null,
    val ownedCards: List<OwnedCard> = emptyList(),
)