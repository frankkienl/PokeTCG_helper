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
import nl.frankkie.poketcghelper.model.*
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
                        Spacer(modifier = Modifier.height(12.dp))
                        PokeTypeComposable(pokeCard.pokeType)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(pokeCard.pokeName)
                        Spacer(modifier = Modifier.height(12.dp))
                        PokeRarityComposable(pokeCard.pokeRarity)
                        Spacer(modifier = Modifier.height(12.dp))
                        PokeFlairComposable(pokeCard.pokeFlair)
                        Spacer(modifier = Modifier.height(12.dp))
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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeTypeComposable(pokeTypeString: String?) {
    if (pokeTypeString == null) {
        return
    }
    val pokeType = PokeType.valueOf(pokeTypeString)
    if (pokeType.imageUrl == null) {
        Text(pokeType.displayName)
        return
    }
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeType) {
        val bytes = Res.readBytes("files/card_symbols/${pokeType.imageUrl}")
        imageBitmap = bytes.decodeToImageBitmap()
    }

    imageBitmap?.let {
        Image(it, contentDescription = pokeType.displayName)
    } ?: run {
        Text(pokeType.displayName)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeRarityComposable(pokeRarityString: String?) {
    if (pokeRarityString == null) {
        return
    }
    val pokeRarity = PokeRarity.valueOf(pokeRarityString)
    if (pokeRarity == PokeRarity.UNKNOWN) {
        return
    }
    //Get image
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeRarity) {
        val bytes = Res.readBytes("files/card_symbols/${pokeRarity.imageUrl}")
        imageBitmap = bytes.decodeToImageBitmap()
    }
    if (imageBitmap == null) {
        Text(pokeRarity.displayName)
    } else {
        Row {
            repeat(pokeRarity.symbolCount ?: 0) {
                imageBitmap?.let {
                    Image(
                        it,
                        contentScale = ContentScale.FillHeight,
                        contentDescription = null,
                        modifier = Modifier.height(30.dp)
                    )
                }
            }
        }
    }
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
                    Image(it, contentDescription = packId, modifier = Modifier.height(80.dp))
                } ?: run {
                    Text(packId)
                }
            }
        } else {
            Text(packId)
        }
    }
}