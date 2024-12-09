package nl.frankkie.poketcghelper.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import nl.frankkie.poketcghelper.AppState
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.compose.homescreen.HomeScreen

// Type-Safe navigation
// https://developer.android.com/guide/navigation/design/type-safety

object Routes {

    @Serializable
    object HomeScreen

    @Serializable
    object LoginScreen

    @Serializable
    object AnalyticsScreen
}

@Composable
fun createNavGraph(navController: NavHostController, appViewModel: AppViewModel, appState: AppState) {
    NavHost(navController, startDestination = Routes.HomeScreen) {
        composable<Routes.HomeScreen> {
            HomeScreen(navController, appViewModel)
        }
        composable<Routes.LoginScreen> {
            LoginScreen(navController, appViewModel)
        }
        composable<Routes.AnalyticsScreen> {
            AnalyticsScreen(navController, appViewModel)
        }
    }
}