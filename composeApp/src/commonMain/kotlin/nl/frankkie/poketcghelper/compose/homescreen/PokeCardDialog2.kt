package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.ktor.http.*
import nl.frankkie.poketcghelper.compose.pokecard_parts.PokeCardAmount
import nl.frankkie.poketcghelper.compose.pokecard_parts.PokePackComposable
import nl.frankkie.poketcghelper.compose.pokecard_parts.PokeRarityComposable
import nl.frankkie.poketcghelper.compose.pokecard_parts.PokeTextRow
import nl.frankkie.poketcghelper.getCurrentPlatform
import nl.frankkie.poketcghelper.isOpenInBrowserSupported
import nl.frankkie.poketcghelper.model.*
import nl.frankkie.poketcghelper.tryToOpenInBrowser
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.MissingResourceException
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeCardDialog2(
    cardDialogData: CardDialogData,
    isLoggedIn: Boolean = false,
    amountOwned: Int = 0,
    isAmountLoading: Boolean = false,
    onChangeAmountOwned: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val pokeCard = cardDialogData.pokeCard
    val pokeCardSet = cardDialogData.pokeCardSet
    val cardHeight = 350.dp
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeCard) {
        try {
            val bytes = Res.readBytes("files/card_images/${pokeCardSet.codeName}/${pokeCard.imageUrl}")
            imageBitmap = bytes.decodeToImageBitmap()
        } catch (missingResourceException: MissingResourceException) {
            println("PokeCardDialog: Failed to load image " + missingResourceException.message)
            //Ignore; No image it is.
        }
    }
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .size(600.dp, cardHeight + 100.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Row {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    imageBitmap?.let {
                        Image(
                            it,
                            modifier = Modifier.height(cardHeight).padding(8.dp),
                            contentScale = ContentScale.Fit,
                            contentDescription = null
                        )
                    }
                    PokeCardAmount(
                        isLoggedIn = isLoggedIn,
                        isAmountLoading = isAmountLoading,
                        onChangeAmountOwned = onChangeAmountOwned,
                        amountOwned = amountOwned,
                    )
                }
                Column(
                    modifier = Modifier.padding(8.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(pokeCard.pokeName, style = MaterialTheme.typography.h4)
                    PokeRarityComposable(pokeCard.pokeRarity)
                    Spacer(modifier = Modifier.height(8.dp))
                    pokeCard.pokeDesc?.let {
                        Text(pokeCard.pokeDesc, style = MaterialTheme.typography.body2)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    pokeCard.pokeFlavour?.let {
                        Text(pokeCard.pokeFlavour, style = MaterialTheme.typography.body2)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    pokeCard.packId?.let {
                        PokePackComposable(pokeCardSet, pokeCard.packId)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    pokeCard.pokeIllustrator?.let {
                        PokeTextRow("Illustrator", pokeCard.pokeIllustrator)
                    }
                    pokeCard.pokeStage?.let {
                        PokeTextRow("Stage", PokeStage.entries[it].displayName)
                        if (pokeCard.pokeStage > 0) {
                            PokeTextRow("Evolves from", pokeCard.pokeEvolvesFrom ?: "UNKNOWN")
                        }
                    }
                    pokeCard.pokeType?.let {
                        val someType = PokeType.valueOf(it)
                        PokeTextRow("Type", null, someType.imageUrl, someType.displayName)
                    }
                    pokeCard.pokeHp?.let {
                        PokeTextRow("HP", it.toString())
                    }
                    pokeCard.pokeWeakness?.let {
                        val someType = PokeType.valueOf(it)
                        PokeTextRow("Weakness", "+20", someType.imageUrl, someType.displayName)
                    }
                    pokeCard.pokeRetreat?.let {
                        val someType = PokeType.COLORLESS
                        PokeTextRow("Retreat", "x${it}", someType.imageUrl, someType.displayName)
                    }
                    pokeCard.pokePrint?.let {
                        val somePrint = PokePrint.valueOf(it)
                        PokeTextRow("Print", somePrint.displayName)
                    }

                    //Bulbapedia
                    if (isOpenInBrowserSupported(getCurrentPlatform())) {
                        OutlinedButton(onClick = { openOnBulbapedia(pokeCard.pokeName, pokeCard.number) }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Open on Bulbapedia")
                                Spacer(Modifier.width(8.dp))
                                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun openOnBulbapedia(pokeName: String, pokeNumber: Int) {
    var nameToOpen = pokeName.replace(" ", "_")
    nameToOpen += "_(Genetic_Apex_${pokeNumber})"
    tryToOpenInBrowser("https://bulbapedia.bulbagarden.net/wiki/${nameToOpen}")
}

