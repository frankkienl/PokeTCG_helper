package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.frankkie.poketcghelper.AppState
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.model.PokeExpansion
import nl.frankkie.poketcghelper.model.PokeExpansionPack
import nl.frankkie.poketcghelper.model.PokeRarity
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

    val ownedCharizardCount = appState.ownedCards.filter { it.pokeCard.packId == "CHARIZARD" && it.amount > 0 }.size
    val ownedMewtwoCount = appState.ownedCards.filter { it.pokeCard.packId == "MEWTWO" && it.amount > 0 }.size
    val ownedPikachuCount = appState.ownedCards.filter { it.pokeCard.packId == "PIKACHU" && it.amount > 0 }.size

    val cardsInPackCharizardNonRare = cardsInPackCharizard.filter { isOfRarityDiamond(it.pokeRarity) }
    val cardsInPackMewtwoNonRare = cardsInPackMewtwo.filter { isOfRarityDiamond(it.pokeRarity) }
    val cardsInPackPikachuNonRare = cardsInPackPikachu.filter { isOfRarityDiamond(it.pokeRarity) }

    val ownedCharizardCountNonRare = appState.ownedCards.filter { it.pokeCard.packId == "CHARIZARD" && it.amount > 0 && isOfRarityDiamond(it.pokeCard.pokeRarity) }.size
    val ownedMewtwoCountNonRare = appState.ownedCards.filter { it.pokeCard.packId == "MEWTWO" && it.amount > 0 && isOfRarityDiamond(it.pokeCard.pokeRarity) }.size
    val ownedPikachuCountNonRare = appState.ownedCards.filter { it.pokeCard.packId == "PIKACHU" && it.amount > 0 && isOfRarityDiamond(it.pokeCard.pokeRarity) }.size

    val ownedMythicalIslandCount = appState.ownedCards.filter { it.pokeExpansion.codeName == "MYTHICAL_ISLAND" && it.amount > 0 }.size

    val uniqueCardsCount = appState.ownedCards.filter { it.amount > 0 }.size
    var totalCardsCount = 0
    appState.ownedCards.forEach { ownedCard -> totalCardsCount += ownedCard.amount }
    Text("You have collected $uniqueCardsCount unique cards")
    Text("You have collected $totalCardsCount total cards")

    Text("You have $ownedCharizardCount cards from Charizard packs")
    Text("You have $ownedMewtwoCount cards from Mewtwo packs")
    Text("You have $ownedPikachuCount cards from Pikachu packs")
    Text("You have $ownedMythicalIslandCount cards from Mythical Island")

    MyHorizontalDivider()

    Text("Genetic Apex\nYou're still missing:")
    val totalCharizardCount = cardsInPackCharizard.size
    val totalMewtwoCount = cardsInPackMewtwo.size
    val totalPikachuCount = cardsInPackPikachu.size
    Text("${totalCharizardCount - ownedCharizardCount} cards total from Charizard packs")
    Text("${cardsInPackCharizardNonRare.size - ownedCharizardCountNonRare} non-rare cards from Charizard packs")
    packProgressBars(geneticApexCardSet, packCharizard, totalCharizardCount, ownedCharizardCount, ownedCharizardCountNonRare)
    Text("${totalMewtwoCount - ownedMewtwoCount} cards total from Mewtwo packs")
    Text("${cardsInPackMewtwoNonRare.size - ownedMewtwoCountNonRare} non-rare cards from Mewtwo packs")
    packProgressBars(geneticApexCardSet, packMewtwo, totalMewtwoCount, ownedMewtwoCount, ownedMewtwoCountNonRare)
    Text("${totalPikachuCount - ownedPikachuCount} cards total from Pikachu packs")
    Text("${cardsInPackPikachuNonRare.size - ownedPikachuCountNonRare} non-rare cards from Pikachu packs")
    packProgressBars(geneticApexCardSet, packPikachu, totalPikachuCount, ownedPikachuCount, ownedPikachuCountNonRare)

    MyHorizontalDivider()

    //MYTHICAL ISLAND
    val mythicalIslandCardSet = appState.cardSets.find { cardSet -> cardSet.codeName == "MYTHICAL_ISLAND" }
    if (mythicalIslandCardSet == null) {
        return
    }
    val totalMythicalIslandCount = mythicalIslandCardSet.numberOfCards
    Text("Mythical Island\nYou're still missing:")
    Text("${totalMythicalIslandCount - ownedMythicalIslandCount} cards from Mythical Island")
    packProgressBars(mythicalIslandCardSet, null, totalMythicalIslandCount, ownedMythicalIslandCount, 0)

    // Evolutions
    // TODO: analytics voor evolutions
}

fun isOfRarityDiamond(rarityString: String?): Boolean {
    if (rarityString == null) return false
    val rarity = PokeRarity.valueOf(rarityString)
    return when (rarity) {
        PokeRarity.D1, PokeRarity.D2, PokeRarity.D3, PokeRarity.D4 -> true
        else -> false
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun packProgressBars(
    expansion: PokeExpansion,
    pack: PokeExpansionPack?,
    totalCardsCount: Int,
    ownedCardCount: Int,
    ownedNonRareCount: Int
) {
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(expansion, pack) {
        val imagePath = if (pack != null) {
            "files/expansions/${expansion.symbol}/expansion_symbols/${pack.imageUrlSymbol}"
        } else {
            "files/expansions/${expansion.symbol}/expansion_symbols/${expansion.imageUrl}"
        }
        val bytes = Res.readBytes(imagePath)
        imageBitmap = bytes.decodeToImageBitmap()
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        imageBitmap?.let {
            Image(it, contentDescription = null, modifier = Modifier.height(50.dp))
        }
        Spacer(Modifier.width(16.dp))
        MultipleLinearProgressIndicator(
            modifier = Modifier.width(500.dp),
            primaryProgress = mapFloat(ownedNonRareCount.toFloat(), 0f, totalCardsCount.toFloat(), 0f, 1f),
            secondaryProgress = mapFloat(ownedCardCount.toFloat(), 0f, totalCardsCount.toFloat(), 0f, 1f),
            clipShape = RoundedCornerShape(16.dp),
            backgroundColor = Color.LightGray
        )
    }
}

fun mapFloat(x: Float, in_min: Float, in_max: Float, out_min: Float, out_max: Float): Float {
    //https://docs.arduino.cc/language-reference/en/functions/math/map/
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
}



@Composable
fun MultipleLinearProgressIndicator(
    modifier: Modifier = Modifier,
    primaryProgress: Float,
    secondaryProgress: Float,
    primaryColor: Color = MaterialTheme.colors.primary,
    secondaryColor: Color = MaterialTheme.colors.secondary,
    backgroundColor: Color = MaterialTheme.colors.background,
    clipShape: Shape = RoundedCornerShape(16.dp)
) {
    //https://medium.com/@eozsahin1993/custom-progress-bars-in-jetpack-compose-723afb60c81c
    Box(
        modifier = modifier
            .clip(clipShape)
            .background(backgroundColor)
            .height(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(secondaryColor)
                .fillMaxHeight()
                .fillMaxWidth(secondaryProgress)
        )
        Box(
            modifier = Modifier
                .background(primaryColor)
                .fillMaxHeight()
                .fillMaxWidth(primaryProgress)
        )
    }
}