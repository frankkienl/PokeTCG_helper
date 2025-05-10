package nl.frankkie.poketcghelper.compose.friends

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.compose.Routes
import nl.frankkie.poketcghelper.supabase.FriendRow
import nl.frankkie.poketcghelper.supabase.dbTableFriends

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FriendsListScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    friendsListScreenViewModel: FriendsListScreenViewModel = viewModel { FriendsListScreenViewModel() }
) {
    val appState = appViewModel.appState.collectAsState().value
    if (appState.supabaseUserInfo == null) {
        //User is not logged in
        println("AnalyticsScreen: not logged in, exit screen;")
        navController.popBackStack()
        return
    }
    val coroutineScope = rememberCoroutineScope()
    val uiState = friendsListScreenViewModel.uiState.collectAsState().value
    LaunchedEffect(null) {
        val supabaseClient = appState.supabaseClient
        val userUid = appState.supabaseUserInfo.id
        if (supabaseClient == null || userUid == null) {
            println("FriendsListScreen: supabaseClient is null, exit screen;")
            navController.popBackStack()
            return@LaunchedEffect
        }
        friendsListScreenViewModel.refreshFriendList(supabaseClient, userUid)
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Poke TCG Helper - Friends list") },
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() },
                    content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") })
            },
            actions = {
                IconButton(
                    onClick = {
                        val supabaseClient = appState.supabaseClient
                        val userUid = appState.supabaseUserInfo.id
                        if (supabaseClient == null || userUid == null) {
                            return@IconButton
                        }
                        coroutineScope.launch {
                            friendsListScreenViewModel.refreshFriendList(supabaseClient, userUid)
                        }
                    },
                    content = { Icon(Icons.Filled.Refresh, contentDescription = "Refresh") })
            }
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Friends list Screen")
            Spacer(Modifier.height(16.dp))
            if (uiState.isLoading) {
                Text("Loading friends list...")
            } else if (uiState.errorMessage != null) {
                Text("Error: ${uiState.errorMessage}")
            } else {
                Column {
                    Text("Your friends: ${uiState.friendsList.size}")
                    // Display the list of friends
                    for (friend in uiState.friendsList) {
                        ListItem(modifier = Modifier.clickable {
                            navController.navigate(Routes.FriendDetailScreen(friend.friend_uid ?: "", friend.friend_email ?: ""))
                        }) { Text(friend.friend_email ?: "") }
                    }
                }
            }
        }
    }
}

class FriendsListScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        FriendsListScreenUiState()
    )
    val uiState = _uiState.asStateFlow()

    fun setFriendsList(friendsList: List<FriendRow>) {
        _uiState.value = _uiState.value.copy(
            friendsList = friendsList,
            isLoading = false,
            errorMessage = null
        )
    }

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

    suspend fun refreshFriendList(
        supabaseClient: SupabaseClient,
        userUid: String
    ) {
        println("FriendsListScreenViewModel: refreshFriendList()")
        val db = supabaseClient.postgrest
        setLoading(true)
        val listOfFriends = db.from(dbTableFriends).select(columns = Columns.ALL) {
            filter {
                eq("user_uid", userUid)
            }
        }.decodeList<FriendRow>()
        println("FriendsListScreenViewModel: refreshFriendList() received ${listOfFriends.size} friends")
        if (listOfFriends.isEmpty()) {
            setErrorMessage("No friends found")
        } else {
            setFriendsList(listOfFriends)
        }
    }
}

data class FriendsListScreenUiState(
    val friendsList: List<FriendRow> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)