package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.compose.MyHorizontalDivider
import nl.frankkie.poketcghelper.compose.Routes

@Composable
fun HomeDrawerContent(navController: NavController, appViewModel: AppViewModel) {
    val rememberCoroutineScope = rememberCoroutineScope()
    val appState = appViewModel.appState.collectAsState().value
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        //Account section
        Text("Account Info:")
        if (appState.supabaseUserInfo == null) {
            Text("Not authenticated")
            Button(onClick = { navController.navigate(Routes.LoginScreen) }) {
                Text("Log in")
            }
        } else {
            Text("${appState.supabaseUserInfo.email}")
            Button(onClick = { rememberCoroutineScope.launch { appViewModel.logout() } }) {
                Text("Log out")
            }
        }
        MyHorizontalDivider()

        // Analytics
        if (appState.supabaseClient != null) {
            Row(
                modifier = Modifier
                    .heightIn(60.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Routes.AnalyticsScreen)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Info, contentDescription = "Info")
                Spacer(Modifier.width(8.dp))
                Text("Analytics")
            }
        }
    }
}