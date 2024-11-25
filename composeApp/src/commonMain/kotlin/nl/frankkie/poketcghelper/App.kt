package nl.frankkie.poketcghelper

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.frankkie.poketcghelper.compose.createNavGraph
import nl.frankkie.poketcghelper.krpc.MyPokeCardsServiceClient
import nl.frankkie.poketcghelper.krpc.MyUser
import nl.frankkie.poketcghelper.model.PokeCardSet
import org.jetbrains.compose.ui.tooling.preview.Preview

lateinit var cardSet: PokeCardSet

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController(),
    appViewModel: AppViewModel = AppViewModel()
) {
    val myCoroutineScope = rememberCoroutineScope()
    LaunchedEffect(null) {
        val myRpc = MyPokeCardsServiceClient(myCoroutineScope)
        appViewModel.setRpc(myRpc)
    }

    MaterialTheme {
        createNavGraph(navController, appViewModel)
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

    fun setMyUser(myUser: MyUser?) {
        _appState.value = _appState.value.copy(
            myUser = myUser
        )
    }
}

data class AppState(
    val myRpc: MyPokeCardsServiceClient? = null,
    val myUser: MyUser? = null
)