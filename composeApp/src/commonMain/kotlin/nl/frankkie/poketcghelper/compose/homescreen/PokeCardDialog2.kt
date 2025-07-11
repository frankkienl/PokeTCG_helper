package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import nl.frankkie.poketcghelper.compose.SimpleAsyncPokeCardImage
import nl.frankkie.poketcghelper.compose.pokecard_parts.PokeCardAmount
import nl.frankkie.poketcghelper.compose.pokecard_parts.PokePackComposable
import nl.frankkie.poketcghelper.compose.pokecard_parts.PokeRarityComposable
import nl.frankkie.poketcghelper.compose.pokecard_parts.PokeTextRow
import nl.frankkie.poketcghelper.model.*
import nl.frankkie.poketcghelper.platform_dependant.getCurrentPlatform
import nl.frankkie.poketcghelper.platform_dependant.isOpenInBrowserSupported
import nl.frankkie.poketcghelper.platform_dependant.tryToOpenInBrowser
import org.jetbrains.compose.resources.ExperimentalResourceApi

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
    val pokeExpansion = cardDialogData.pokeExpansion
    val cardHeight = 300.dp
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .size(600.dp, cardHeight + 100.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            BoxWithConstraints {
                println("maxWidth: $maxWidth; maxHeight: $maxHeight")
                if (maxWidth <= 424.dp) {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        PokeCardDialog2_CardImagePart(pokeCard = pokeCard, pokeExpansion = pokeExpansion, cardHeight, isLoggedIn, isAmountLoading, onChangeAmountOwned, amountOwned, false)
                        PokeCardDialog2_CardDetailsPart(pokeCard, pokeExpansion, false)
                    }
                } else {
                    Row {
                        PokeCardDialog2_CardImagePart(pokeCard = pokeCard, pokeExpansion = pokeExpansion, cardHeight, isLoggedIn, isAmountLoading, onChangeAmountOwned, amountOwned, true)
                        PokeCardDialog2_CardDetailsPart(pokeCard, pokeExpansion, true)
                    }
                }
            }
        }
    }
}

@Composable
private fun PokeCardDialog2_CardDetailsPart(pokeCard: PokeCard, pokeExpansion: PokeExpansion, isHorizontal: Boolean) {
    Column(
        modifier = if (isHorizontal) {
            Modifier.padding(8.dp).verticalScroll(rememberScrollState())
        } else {
            Modifier.padding(8.dp)
        },
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
            PokePackComposable(pokeExpansion, pokeCard.packId)
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
            OutlinedButton(onClick = { openOnBulbapedia(pokeCard, pokeExpansion) }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Open on Bulbapedia")
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                }
            }
        }
    }
}

@Composable
private fun PokeCardDialog2_CardImagePart(
    pokeCard: PokeCard,
    pokeExpansion: PokeExpansion,
    cardHeight: Dp,
    isLoggedIn: Boolean,
    isAmountLoading: Boolean,
    onChangeAmountOwned: (Int) -> Unit,
    amountOwned: Int,
    isHorizontal: Boolean
) {
    Column(
        modifier = if (isHorizontal) {
            Modifier
        } else {
            Modifier.fillMaxWidth()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleAsyncPokeCardImage(modifier = Modifier.height(cardHeight).padding(8.dp),
            pokeCard = pokeCard, pokeExpansion = pokeExpansion, cardPlaceholderImage = null)
        PokeCardAmount(
            isLoggedIn = isLoggedIn,
            isAmountLoading = isAmountLoading,
            onChangeAmountOwned = onChangeAmountOwned,
            amountOwned = amountOwned,
        )
    }
}

fun openOnBulbapedia(pokeCard: PokeCard, pokeExpansion: PokeExpansion) {
    val pokeName = pokeCard.pokeName
    val pokeNumber = pokeCard.number
    val pokeExpansionDisplayName = pokeExpansion.displayName
    val nameToOpen = "${pokeName}_(${pokeExpansionDisplayName}_${pokeNumber})".replace(" ", "_")
    tryToOpenInBrowser("https://bulbapedia.bulbagarden.net/wiki/${nameToOpen}")
}

