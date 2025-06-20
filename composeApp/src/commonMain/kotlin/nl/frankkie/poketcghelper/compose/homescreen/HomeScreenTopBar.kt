package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.DrawerState
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.compose.Routes

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
    var showOverflowMenu by remember { mutableStateOf(false) }
    val isLoggedIn = appState.supabaseUserInfo != null
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
            IconButton(onClick = { homeScreenViewModel.showFilterDialog() }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }

            IconButton(onClick = { showOverflowMenu = !showOverflowMenu }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More options")
            }
            DropdownMenu(
                expanded = showOverflowMenu,
                onDismissRequest = { showOverflowMenu = false }
            ) {

                if (isLoggedIn) {

                    // Enable/disable number input mode
                    DropdownMenuItem(onClick = { homeScreenViewModel.setAmountInputMode(!homeScreenUiState.amountInputMode); showOverflowMenu = false }) {
                        Row {
                            Icon(
                                if (homeScreenUiState.amountInputMode) {
                                    Icons.Filled.Add
                                } else {
                                    Icons.Outlined.Add
                                },
                                contentDescription = "Number input mode"
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                if (homeScreenUiState.amountInputMode) {
                                    "Disable"
                                } else {
                                    "Enable"
                                } + " number input mode"
                            )
                        }
                    }

                    // Friends
                    if (appState.supabaseClient != null) {
                        DropdownMenuItem(onClick = { homeScreenViewModel.showFriendDialog(true); showOverflowMenu = false }) {
                            Row {
                                Icon(Icons.Default.Person, contentDescription = "Friend")
                                Spacer(Modifier.width(8.dp))
                                Text("Compare with Friend")
                            }
                        }
                    }

                    if (appState.supabaseClient != null) {
                        // Refresh cards
                        DropdownMenuItem(onClick = { rememberCoroutineScope.launch { appViewModel.refreshOwnedCards() }; showOverflowMenu = false }) {
                            Row {
                                Icon(Icons.Filled.Refresh, contentDescription = "Refresh owned cards")
                                Spacer(Modifier.width(8.dp))
                                Text("Refresh owned cards")
                            }
                        }
                    }
                }

                // Account
                if (appState.supabaseUserInfo != null) {
                    // Logout
                    DropdownMenuItem(onClick = { homeScreenViewModel.showLogoutDialog(true); showOverflowMenu = false }) {
                        Row {
                            Icon(Icons.Outlined.AccountCircle, contentDescription = "Logout icon")
                            Spacer(Modifier.width(8.dp))
                            Text("Logout")
                        }
                    }
                } else {
                    // Login
                    DropdownMenuItem(onClick = { navController.navigate(Routes.LoginScreen); showOverflowMenu = false }) {
                        Row {
                            Icon(Icons.Outlined.AccountCircle, contentDescription = "Login icon")
                            Spacer(Modifier.width(8.dp))
                            Text("Login")
                        }
                    }
                }
            }
        }
    )
}