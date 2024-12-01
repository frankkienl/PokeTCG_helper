package nl.frankkie.poketcghelper

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.frankkie.poketcghelper.compose.createNavGraph
import nl.frankkie.poketcghelper.krpc.MyPokeCardsServiceClient
import nl.frankkie.poketcghelper.krpc.MyUser
import nl.frankkie.poketcghelper.model.PokeCardSet
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController(),
    appViewModel: AppViewModel = viewModel { AppViewModel() }
) {
    val myCoroutineScope = rememberCoroutineScope()
    LaunchedEffect(null) {
        //val myRpc = MyPokeCardsServiceClient(myCoroutineScope)
        //appViewModel.setRpc(myRpc)
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

    fun setRpc(myRpc: MyPokeCardsServiceClient?) {
        _appState.value = _appState.value.copy(
            myRpc = myRpc
        )
    }

    fun login(username:String) {
        _appState.value = _appState.value.copy(
            myUser = MyUser(username = username, ownedCards = emptyMap())
        )
        //get owned card from server

    }

    fun setMyUser(myUser: MyUser?) {
        _appState.value = _appState.value.copy(
            myUser = myUser
        )
    }

    fun setCardSets(cardSets: List<PokeCardSet>) {
        _appState.value = _appState.value.copy(
            cardSets = cardSets
        )
    }
}

data class AppState(
    val cardSets: List<PokeCardSet> = emptyList(),
    val myRpc: MyPokeCardsServiceClient? = null,
    val myUser: MyUser? = null
)