package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.compose.MyHorizontalDivider
import nl.frankkie.poketcghelper.compose.Routes
import nl.frankkie.poketcghelper.platform_dependant.getCurrentPlatform
import nl.frankkie.poketcghelper.platform_dependant.isRemoteControlClientSupported
import nl.frankkie.poketcghelper.platform_dependant.isRemoteControlHostSupported
import nl.frankkie.poketcghelper.platform_dependant.tryToOpenRemoteControlClient

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
        if (appState.supabaseClient != null && appState.supabaseUserInfo != null) {
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

            MyHorizontalDivider()

            // Friends
            Row(
                modifier = Modifier
                    .heightIn(60.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Routes.FriendsListScreen)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Person, contentDescription = "Friends")
                Spacer(Modifier.width(8.dp))
                Text("Friends")
            }

            MyHorizontalDivider()
        }

        //Android Remote Control Client
        val isRemoteClientReadyForUse = false
        if (isRemoteClientReadyForUse && isRemoteControlClientSupported(getCurrentPlatform())) {
            Row(
                modifier = Modifier
                    .heightIn(60.dp)
                    .fillMaxWidth()
                    .clickable {
                        tryToOpenRemoteControlClient()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Info, contentDescription = "Info")
                Spacer(Modifier.width(8.dp))
                Text("Remote Control Client")
            }
        }

        // Desktop Remote Control Host
        if (isRemoteClientReadyForUse && isRemoteControlHostSupported(getCurrentPlatform())) {
            Row(
                modifier = Modifier
                    .heightIn(60.dp)
                    .fillMaxWidth()
                    .clickable {

                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Info, contentDescription = "Info")
                Spacer(Modifier.width(8.dp))
                Text("Remote Control Host")
            }
        }

    }
}