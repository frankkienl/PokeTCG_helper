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
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import nl.frankkie.poketcghelper.compose.createNavGraph
import nl.frankkie.poketcghelper.model.PokeCardSet
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
}

data class AppState(
    val cardSets: List<PokeCardSet> = emptyList(),
    val supabaseClient: SupabaseClient? = null,
    val supabaseUserInfo: UserInfo? = null
)