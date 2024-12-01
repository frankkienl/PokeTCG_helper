package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.navigation.NavHostController
import nl.frankkie.poketcghelper.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * For future use;
 * If you log in,
 * you can save your list of owned cards,
 * and backup that up to the server.
 */

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    appViewModel: AppViewModel
) {
    val appState = appViewModel.appState.collectAsState().value

    if (appState.myUser != null) {
        //User is already logged in
        navHostController.popBackStack()

    }

    var isLoading by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Poke TCG Helper - Login") },
            navigationIcon = {
                IconButton(
                    onClick = { navHostController.popBackStack() },
                    content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") })
            },
        )
    }) {
        Column {
            Text("Login form")
            Text("Note: There is no security. None. Zero. 0.0;")
            Text("That's why there's no password field, just the username field.")
            Text("Maybe someday, I'm open for suggestions!")
            Spacer(Modifier.height(16.dp))

            if (appState.myRpc == null) {
                Text("Warning: No connection to server!", color = Color.Red)
                Spacer(Modifier.height(16.dp))
            }

            if (!isLoading) {
                var username by remember { mutableStateOf("") }
                OutlinedTextField(username, onValueChange = { username = it })

                if (loginError != null) {
                    Text("Login error:\n$loginError", color = Color.Red)
                    Spacer(Modifier.height(16.dp))
                }

                OutlinedButton(onClick = {
                    isLoading = true
                    navHostController.popBackStack()
                }, content = { Text("Login") })
            } else {
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
                Text("Logging in...")
            }
        }
    }
}

fun login(appViewModel: AppViewModel, username: String) {
    appViewModel.login(username)
}
