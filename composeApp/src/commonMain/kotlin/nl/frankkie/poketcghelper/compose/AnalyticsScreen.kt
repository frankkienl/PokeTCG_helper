package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.frankkie.poketcghelper.AppState
import nl.frankkie.poketcghelper.AppViewModel

@Composable
fun AnalyticsScreen(navController: NavController, appViewModel: AppViewModel) {
    val appState = appViewModel.appState.collectAsState().value

    if (appState.supabaseUserInfo == null) {
        //User is not logged in
        println("AnalyticsScreen: not logged in, exit screen;")
        navController.popBackStack()
        return
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Poke TCG Helper - Analytics") },
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() },
                    content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") })
            },
        )
    }) { innerPadding ->

        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Text("Analytics Screen")
            Spacer(Modifier.height(16.dp))
            ana1(appState)
        }
    }
}

@Composable
fun ana1(appState: AppState) {
    if (appState.supabaseUserInfo == null) {
        return
    }
    val uniqueCardsCount = appState.ownedCards.filter { it.amount > 0 }.size
    var totalCardsCount = 0
    appState.ownedCards.forEach { ownedCard -> totalCardsCount += ownedCard.amount }
    Text("You have collected $uniqueCardsCount unique cards")
    Text("You have collected $totalCardsCount total cards")
    val ownedCharizardCount = appState.ownedCards.filter { it.pokeCard.packId == "CHARIZARD" && it.amount > 0 }.size
    val ownedMewtwoCount = appState.ownedCards.filter { it.pokeCard.packId == "MEWTWO" && it.amount > 0 }.size
    val ownedPikachuCount = appState.ownedCards.filter { it.pokeCard.packId == "PIKACHU" && it.amount > 0 }.size
    Text("You have $ownedCharizardCount cards from Charizard packs")
    Text("You have $ownedMewtwoCount cards from Mewtwo packs")
    Text("You have $ownedPikachuCount cards from Pikachu packs")

    MyHorizontalDivider()

    val geneticApexCardSet = appState.cardSets.find { cardSet -> cardSet.codeName == "GENETIC_APEX" }
    if (geneticApexCardSet == null) {
        return
    }
    Text("You're still missing:")
    val totalCharizardCount = geneticApexCardSet.cards.filter { it.packId == "CHARIZARD" }.size
    val totalMewtwoCount = geneticApexCardSet.cards.filter { it.packId == "MEWTWO" }.size
    val totalPikachuCount = geneticApexCardSet.cards.filter { it.packId == "PIKACHU" }.size
    Text("${totalCharizardCount - ownedCharizardCount} cards from Charizard packs")
    Text("${totalMewtwoCount - ownedMewtwoCount} cards from Mewtwo packs")
    Text("${totalPikachuCount - ownedPikachuCount} cards from Pikachu packs")
}