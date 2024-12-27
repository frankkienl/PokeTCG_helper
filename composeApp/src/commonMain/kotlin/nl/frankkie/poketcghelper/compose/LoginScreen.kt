package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.navigation.NavHostController
import nl.frankkie.poketcghelper.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.exception.AuthWeakPasswordException
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

/**
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

    if (appState.supabaseUserInfo != null) {
        //User is already logged in
        println("LoginScreen: already logged in, exit screen; ${appState.supabaseUserInfo}")
        navHostController.popBackStack()
        return
    }

    val myCoroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
            Spacer(Modifier.height(16.dp))

            if (appState.supabaseClient == null) {
                Text("Warning: No connection to server!", color = Color.Red)
                Spacer(Modifier.height(16.dp))
            }

            var userEmail by remember { mutableStateOf("") }
            var userPassword by remember { mutableStateOf("") }
            var passwordVisible by remember { mutableStateOf(false) }

            TextField(
                onValueChange = { userEmail = it },
                value = userEmail,
                label = { Text("Email") },
                enabled = !isLoading,
                singleLine = true,
                placeholder = { Text("Email") },
            )
            TextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                label = { Text("Password") },
                enabled = !isLoading,
                singleLine = true,
                placeholder = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Check
                    else Icons.Filled.CheckCircle
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, description)
                    }
                }
            )

            if (hasError) {
                Text("Login Error", color = Color.Red)
                if (!errorMessage.isNullOrBlank()) {
                    Text(errorMessage!!, color = Color.Red)
                }
            }

            Spacer(Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator()
            }

            OutlinedButton(
                enabled = !isLoading,
                onClick = {
                    isLoading = true
                    myCoroutineScope.launch {
                        try {
                            val user = appState.supabaseClient?.auth?.signUpWith(Email) {
                                email = userEmail
                                password = userPassword
                            }
                            if (user != null) {
                                isLoading = false
                                hasError = false
                                appViewModel.setSupabaseUserInfo(user)
                                println("LoginScreen: Sign up successful; $user")
                                navHostController.popBackStack()
                            }
                        } catch (e: Exception) {
                            //Weak password
                            if (e is AuthWeakPasswordException) {
                                println("LoginScreen: Sign up failed, weak password")
                                println(e.message)
                                isLoading = false
                                hasError = true
                                val weakPasswordReasons = e.reasons.reduce { acc, line -> acc + line + "\n" }
                                errorMessage = "Weak password\n${e.message}\n$weakPasswordReasons"
                                return@launch
                            }
                            //User already exists (?)
                            if (e is AuthRestException) {
                                println("LoginScreen: Sign up failed")
                                println(e.message)
                                isLoading = false
                                hasError = true
                                errorMessage = e.message
                            }
                        }
                    }
                },
                content = { Text("Sign Up") }
            )

            OutlinedButton(
                enabled = !isLoading,
                onClick = {
                    isLoading = true
                    myCoroutineScope.launch {
                        appState.supabaseClient?.auth?.signInWith(Email) {
                            email = userEmail
                            password = userPassword
                        }
                    }
                },
                content = { Text("Login") }
            )
        }
    }
}
