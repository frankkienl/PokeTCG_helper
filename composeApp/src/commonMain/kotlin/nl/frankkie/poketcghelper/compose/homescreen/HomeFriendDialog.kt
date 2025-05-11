package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.compose.MyHorizontalDivider
import nl.frankkie.poketcghelper.supabase.FriendRow
import nl.frankkie.poketcghelper.supabase.dbTableFriends
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeFriendDialog(
    onFriendSelected: (String?, String?) -> Unit,
    onClose: () -> Unit,
    appViewModel: AppViewModel,
    homeFriendDialogViewModel: HomeFriendDialogViewModel = viewModel { HomeFriendDialogViewModel() }
) {
    val uiState = homeFriendDialogViewModel.uiState.collectAsState().value
    val appState = appViewModel.appState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(null) {
        if (appState.supabaseClient == null || appState.supabaseUserInfo == null) {
            println("HomeFriendDialog: supabaseClient is null, or supabaseUserInfo is null, exit dialog;")
            onClose()
            return@LaunchedEffect
        }
        homeFriendDialogViewModel.refreshFriendList(appState.supabaseClient, appState.supabaseUserInfo.id)
    }
    Dialog(onDismissRequest = onClose) {
        Card(modifier = Modifier.padding(8.dp).widthIn(250.dp), shape = RoundedCornerShape(8.dp)) {
            HomeFriendDialogContent(
                onFriendSelected = onFriendSelected,
                onClose = onClose,
                isLoading = uiState.isLoading,
                errorMessage = uiState.errorMessage,
                listOfFriends = uiState.friendsList
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeFriendDialogContent(
    onFriendSelected: (String?, String?) -> Unit = {_,_ -> },
    onClose: () -> Unit = {},
    isLoading: Boolean = false,
    errorMessage: String? = null,
    listOfFriends: List<FriendRow> = emptyList(),
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.padding(8.dp).verticalScroll(scrollState)) {
        Text("Compare with friend", style = MaterialTheme.typography.h6)
        Text("Select a friend to compare your collection with.")
        MyHorizontalDivider()
        if (isLoading) {
            Text("Loading friends list...")
        } else if (errorMessage != null) {
            Text("Error: $errorMessage")
        } else {
            Column {
                ListItem(modifier = Modifier.clickable {
                    onFriendSelected(null, null)
                    onClose()
                }, icon = {
                    Icon(Icons.Default.Close, contentDescription = "None")
                }) { Text("None") }
                // Display the list of friends
                for (friend in listOfFriends) {
                    ListItem(modifier = Modifier.clickable {
                        onFriendSelected(friend.friend_uid ?: "", friend.friend_email ?: "")
                        onClose()
                    }, icon = {
                        Icon(Icons.Default.Person, contentDescription = "Friend")
                    }) { Text(friend.friend_email ?: "") }
                }
            }
        }
    }
}


@Composable
@Preview
fun HomeFriendDialogPreview() {
    HomeFriendDialogContent(
        onFriendSelected = { _, _ -> },
        onClose = {},
        isLoading = false,
        errorMessage = null,
        listOfFriends = listOf(
            FriendRow(user_uid = "", friend_uid = "1", friend_email = "Friend1@example.com"),
            FriendRow(user_uid = "", friend_uid = "2", friend_email = "Friend2@example.com"),
        )
    )
}

class HomeFriendDialogViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeFriendDialogState()
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
        println("HomeFriendDialogViewModel: refreshFriendList()")
        val db = supabaseClient.postgrest
        setLoading(true)
        val listOfFriends = db.from(dbTableFriends).select(columns = Columns.ALL) {
            filter {
                eq("user_uid", userUid)
            }
        }.decodeList<FriendRow>()
        println("HomeFriendDialogViewModel: refreshFriendList() received ${listOfFriends.size} friends")
        if (listOfFriends.isEmpty()) {
            setErrorMessage("No friends found")
        } else {
            setFriendsList(listOfFriends)
        }
    }
}

data class HomeFriendDialogState(
    val friendsList: List<FriendRow> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)