package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import nl.frankkie.poketcghelper.AppViewModel

@Composable
fun HomeScreenLogoutDialog(appViewModel: AppViewModel, homeScreenViewModel: HomeScreenViewModel) {
    Dialog(
        onDismissRequest = { homeScreenViewModel.showLogoutDialog(false) },
    ) {
        val rememberCoroutineScope = rememberCoroutineScope()
        val appState = appViewModel.appState.collectAsState().value
        if (appState.supabaseUserInfo == null) {
            //Already logged out
            println("LogoutDialog: Already logged out")
            homeScreenViewModel.showLogoutDialog(false)
        }
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
                Text(appState.supabaseUserInfo?.email ?: "Anonymous")
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
                                    //clear owned cards filter
                                    homeScreenViewModel.setCardFilter(
                                        homeScreenViewModel.uiState.value.cardFilter.copy(
                                            ownedStatus = emptyList()
                                        )
                                    )
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