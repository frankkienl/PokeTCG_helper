package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeCardSet
import nl.frankkie.poketcghelper.model.PokeCardSetPack
import nl.frankkie.poketcghelper.model.PokeFlair
import nl.frankkie.poketcghelper.model.PokeRarity
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeCardDialog(
    pokeCardSet: PokeCardSet,
    pokeCard: PokeCard,
    isLoggedIn: Boolean = false,
    amountOwned: Int = 0,
    onChangeAmountOwned: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val cardHeight = 250.dp
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeCard) {
        val bytes = Res.readBytes("files/card_images2/${pokeCard.imageUrl.replace("-143x200", "")}")
        imageBitmap = bytes.decodeToImageBitmap()
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
                        modifier = Modifier.height(cardHeight).padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(pokeCard.number.toString())
                        Text(pokeCard.pokeName)
                        PokeRarityComposable(pokeCard.pokeRarity)
                        PokeFlairComposable(pokeCard.pokeFlair)
                        PokePackComposable(pokeCardSet, pokeCard.packId)

                        if (isLoggedIn) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Amount owned:")
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                OutlinedButton(onClick = { onChangeAmountOwned(amountOwned - 1) }) {
                                    Text("-")
                                }
                                Text(
                                    amountOwned.toString(),
                                    modifier = Modifier.padding(8.dp)
                                )
                                OutlinedButton(onClick = { onChangeAmountOwned(amountOwned - 1) }) {
                                    Text("+")
                                }
                            }
                        }
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

@Composable
fun PokeRarityComposable(pokeRarityString: String) {
    val pokeRarity = PokeRarity.valueOf(pokeRarityString)
    Text(pokeRarity.displayName)
}

@Composable
fun PokeFlairComposable(pokeFlairString: String?) {
    if (pokeFlairString != null) {
        val pokeFlair = PokeFlair.valueOf(pokeFlairString)
        Text(pokeFlair.displayName)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokePackComposable(pokeCardSet: PokeCardSet?, packId: String?) {
    if (packId != null) {
        if (pokeCardSet != null) {
            pokeCardSet.packs.find { somePack -> somePack.id == packId }?.let { thePack ->
                var imageBitmap by remember {
                    mutableStateOf<ImageBitmap?>(null)
                }
                LaunchedEffect(pokeCardSet, packId) {
                    val bytes = Res.readBytes("files/card_symbols/${thePack.imageUrlSymbol}")
                    imageBitmap = bytes.decodeToImageBitmap()
                }

                imageBitmap?.let {
                    Image(it, contentDescription = packId)
                } ?: run {
                    Text(packId)
                }
            }
        } else {
            Text(packId)
        }
    }
}