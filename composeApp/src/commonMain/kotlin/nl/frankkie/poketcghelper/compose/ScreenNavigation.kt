package nl.frankkie.poketcghelper.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
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

    @Serializable
    data class CardEditScreen(val cardSetCodeName: String, val cardNumber: Int)
}

@Composable
fun createNavGraph(navController: NavHostController, appViewModel: AppViewModel) {
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
        composable<Routes.CardEditScreen> { backStackEntry ->
            val data = backStackEntry.toRoute<Routes.CardEditScreen>()
            CardEditScreen(navController, appViewModel, data.cardSetCodeName, data.cardNumber)
        }
    }
}