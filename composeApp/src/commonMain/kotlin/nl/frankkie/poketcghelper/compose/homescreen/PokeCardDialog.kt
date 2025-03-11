package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import nl.frankkie.poketcghelper.compose.pokecard_parts.*
import nl.frankkie.poketcghelper.model.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.MissingResourceException
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeCardDialog(
    cardDialogData: CardDialogData,
    isLoggedIn: Boolean = false,
    amountOwned: Int = 0,
    isAmountLoading: Boolean = false,
    onChangeAmountOwned: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val cardHeight = 350.dp
    val pokeCard = cardDialogData.pokeCard
    val pokeExpansion = cardDialogData.pokeExpansion
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeCard) {
        try {
            // Try small version
            val bytes = Res.readBytes("files/expansions/${pokeExpansion.symbol}/card_images_small/${pokeCard.imageUrl}")
            imageBitmap = bytes.decodeToImageBitmap()
        } catch (e: Exception) {
            println("PokeCardDialog: Failed to load image (small) " + e.message)
            try {
                // Try large version
                val bytes = Res.readBytes("files/expansions/${pokeExpansion.symbol}/card_images/${pokeCard.imageUrl}")
                imageBitmap = bytes.decodeToImageBitmap()
            } catch (e: Exception) {
                println("PokeCardDialog: Failed to load image (large) " + e.message)
                // Image doesn't work.
                imageBitmap = null
            }
        }
    }
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row {
                    imageBitmap?.let {
                        Image(
                            it,
                            modifier = Modifier.height(cardHeight).padding(8.dp),
                            contentScale = ContentScale.Fit,
                            contentDescription = null
                        )
                    }
                    Column(
                        modifier = Modifier.height(cardHeight).widthIn(250.dp).padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(pokeCard.number.toString())
                        Spacer(modifier = Modifier.height(12.dp))
                        PokeTypeComposable(pokeCard.pokeType)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(pokeCard.pokeName)
                        Spacer(modifier = Modifier.height(12.dp))
                        PokeRarityComposable(pokeCard.pokeRarity)
                        Spacer(modifier = Modifier.height(12.dp))
                        PokePrintComposable(pokeCard.pokePrint)
                        Spacer(modifier = Modifier.height(12.dp))
                        PokePackComposable(pokeExpansion, pokeCard.packId)

                        PokeCardAmount(isLoggedIn, isAmountLoading, onChangeAmountOwned, amountOwned)
                    }
                }
                Row {
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }

}


data class CardDialogData(
    val pokeExpansion: PokeExpansion,
    val pokeCard: PokeCard
)