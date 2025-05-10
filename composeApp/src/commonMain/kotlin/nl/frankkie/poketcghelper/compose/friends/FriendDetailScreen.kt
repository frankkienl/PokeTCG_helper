package nl.frankkie.poketcghelper.compose.friends

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.frankkie.poketcghelper.AppViewModel

@Composable
fun FriendDetailScreen(
    navController: NavController,
    appViewModel: AppViewModel,
) {
    val appState = appViewModel.appState.collectAsState().value
    if (appState.supabaseUserInfo == null) {
        //User is not logged in
        println("AnalyticsScreen: not logged in, exit screen;")
        navController.popBackStack()
        return
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
            Text("Friends detail Screen")
            Spacer(Modifier.height(16.dp))
        }
    }
}