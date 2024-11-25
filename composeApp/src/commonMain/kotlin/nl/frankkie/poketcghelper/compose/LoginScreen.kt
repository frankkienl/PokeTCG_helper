package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import nl.frankkie.poketcghelper.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import nl.frankkie.poketcghelper.krpc.MyUser

/**
 * For future use;
 * If you log in,
 * you can save your list of owned cards,
 * and backup that up to the server.
 */

@Composable
fun LoginScreen(navHostController: NavHostController, appViewModel: AppViewModel) {
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
            Text("TODO - Login form")
            OutlinedTextField("TODO", {})
            OutlinedButton(onClick = {
                appViewModel.setMyUser(MyUser("frankkienl"))
                navHostController.popBackStack()
            }, content = { Text("Login") })
        }
    }
}