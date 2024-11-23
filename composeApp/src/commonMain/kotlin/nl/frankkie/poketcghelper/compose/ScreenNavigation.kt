package nl.frankkie.poketcghelper.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// Type-Safe navigation
// https://developer.android.com/guide/navigation/design/type-safety

object Routes {

    @Serializable
    object HomeScreen

    @Serializable
    object LoginScreen
}

@Composable
fun createNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.HomeScreen) {
        composable<Routes.HomeScreen> {
            HomeScreen(navController)
        }
        composable<Routes.LoginScreen> {
            LoginScreen(navController)
        }
    }
}