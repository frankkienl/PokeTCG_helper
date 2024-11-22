package nl.frankkie.poketcghelper

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeCardSet
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import poketcg_helper.composeapp.generated.resources.Res
import poketcg_helper.composeapp.generated.resources.compose_multiplatform

lateinit var cardSet: PokeCardSet

@Composable
@Preview
fun App() {
    MaterialTheme {
        var cardsInitialized by remember { mutableStateOf(false) }
        LaunchedEffect(null) {
            if (!cardsInitialized) {
                cardSet = initializeCards()
                cardsInitialized = true
            }
        }
        Scaffold(
            topBar = { TopAppBar(title = { Text("Poke TCG Helper") }) },
        ) {
            if (!cardsInitialized) {
                Text("Loading...")
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(5),
                ) {
                    items(cardSet.cards) { card ->
                        PokeCardComposable(card)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeCardComposable(pokeCard: PokeCard) {
    var bytes by remember {
        mutableStateOf(ByteArray(0))
    }
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeCard) {
        bytes = Res.readBytes("files/card_images/${pokeCard.imageUrl}")
        imageBitmap = bytes.decodeToImageBitmap()
    }
    Column(
        modifier = Modifier.border(1.dp, Color.Black)
    ) {
        imageBitmap?.let {
            Image(it, null)
        }
        Text(pokeCard.number.toString())
        Text(pokeCard.pokeName)
        Text(pokeCard.packId ?: "")
    }
}

@Composable
fun App_old() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}