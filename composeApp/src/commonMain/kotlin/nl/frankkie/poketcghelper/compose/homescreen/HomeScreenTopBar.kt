package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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