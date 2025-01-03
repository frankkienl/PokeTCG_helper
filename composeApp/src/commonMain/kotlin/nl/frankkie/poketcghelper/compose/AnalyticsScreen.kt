package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.frankkie.poketcghelper.AppState
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.model.PokeCardSet
import nl.frankkie.poketcghelper.model.PokeCardSetPack
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
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
    val ownedMythicalIslandCount = appState.ownedCards.filter { it.pokeCardSet.codeName == "MYTHICAL_ISLAND" && it.amount > 0 }.size
    Text("You have $ownedCharizardCount cards from Charizard packs")
    Text("You have $ownedMewtwoCount cards from Mewtwo packs")
    Text("You have $ownedPikachuCount cards from Pikachu packs")
    Text("You have $ownedMythicalIslandCount cards from Mythical Island")

    MyHorizontalDivider()

    // GENETIC APEX
    val geneticApexCardSet = appState.cardSets.find { cardSet -> cardSet.codeName == "GENETIC_APEX" }
    if (geneticApexCardSet == null) {
        return
    }
    val packCharizard = geneticApexCardSet.packs.find { it.id == "CHARIZARD" }!!
    val packMewtwo = geneticApexCardSet.packs.find { it.id == "MEWTWO" }!!
    val packPikachu = geneticApexCardSet.packs.find { it.id == "PIKACHU" }!!

    val cardsInPackCharizard = geneticApexCardSet.cards.filter { it.packId == "CHARIZARD" }
    val cardsInPackMewtwo = geneticApexCardSet.cards.filter { it.packId == "MEWTWO" }
    val cardsInPackPikachu = geneticApexCardSet.cards.filter { it.packId == "PIKACHU" }

    Text("Genetic Apex\nYou're still missing:")
    val totalCharizardCount = cardsInPackCharizard.size
    val totalMewtwoCount = cardsInPackMewtwo.size
    val totalPikachuCount = cardsInPackPikachu.size
    Text("${totalCharizardCount - ownedCharizardCount} cards from Charizard packs")
    Text("${totalMewtwoCount - ownedMewtwoCount} cards from Mewtwo packs")
    Text("${totalPikachuCount - ownedPikachuCount} cards from Pikachu packs")

    // Progress bars
    packProgressBars(geneticApexCardSet, packCharizard, totalCharizardCount, ownedCharizardCount)
    packProgressBars(geneticApexCardSet, packMewtwo, totalMewtwoCount, ownedMewtwoCount)
    packProgressBars(geneticApexCardSet, packPikachu, totalPikachuCount, ownedPikachuCount)

    MyHorizontalDivider()

    //MYTHICAL ISLAND
    val mythicalIslandCardSet = appState.cardSets.find { cardSet -> cardSet.codeName == "MYTHICAL_ISLAND" }
    if (mythicalIslandCardSet == null) {
        return
    }
    val totalMythicalIslandCount = mythicalIslandCardSet.numberOfCards
    Text("Mythical Island\nYou're still missing:")
    Text("${totalMythicalIslandCount - ownedMythicalIslandCount} cards from Mythical Island")
    packProgressBars(mythicalIslandCardSet, null, totalMythicalIslandCount, ownedMythicalIslandCount)

    // Evolutions
    // TODO: analytics voor evolutions
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun packProgressBars(cardSet: PokeCardSet, pack: PokeCardSetPack?, totalCardsCount: Int, ownedCardCount: Int) {
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(cardSet, pack) {
        val imagePath = if (pack!=null) {
           "files/card_symbols/${cardSet.codeName}/${pack.imageUrlSymbol}"
        } else {
            "files/card_symbols/${cardSet.codeName}/${cardSet.imageUrl}"
        }
        val bytes = Res.readBytes(imagePath)
        imageBitmap = bytes.decodeToImageBitmap()
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        imageBitmap?.let {
            Image(it, contentDescription = null, modifier = Modifier.height(50.dp))
        }
        Spacer(Modifier.width(16.dp))
        val progressFloat = mapFloat(ownedCardCount.toFloat(), 0f, totalCardsCount.toFloat(), 0f, 1f)
        LinearProgressIndicator(
            progress = progressFloat,
            modifier = Modifier.width(200.dp)
        )
    }
}

fun mapFloat(x: Float, in_min: Float, in_max: Float, out_min: Float, out_max: Float): Float {
    //https://docs.arduino.cc/language-reference/en/functions/math/map/
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
}