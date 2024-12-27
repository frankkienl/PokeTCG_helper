package nl.frankkie.poketcghelper.compose.pokecard_parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import nl.frankkie.poketcghelper.model.PokeCardSet
import nl.frankkie.poketcghelper.model.PokePrint
import nl.frankkie.poketcghelper.model.PokeRarity
import nl.frankkie.poketcghelper.model.PokeType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

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
        Image(
            it,
            contentScale = ContentScale.FillHeight,
            contentDescription = pokeType.displayName,
            modifier = Modifier.height(30.dp)
        )
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
        if (pokeRarity.imageUrl != null) {
            val bytes = Res.readBytes("files/card_symbols/${pokeRarity.imageUrl}")
            imageBitmap = bytes.decodeToImageBitmap()
        }
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
                        modifier = Modifier.height(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PokePrintComposable(pokePrintString: String?) {
    if (pokePrintString != null) {
        val pokePrint = PokePrint.valueOf(pokePrintString)
        Text(pokePrint.displayName)
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
                    val bytes = Res.readBytes("files/card_symbols/${pokeCardSet.codeName}/${thePack.imageUrlSymbol}")
                    imageBitmap = bytes.decodeToImageBitmap()
                }

                imageBitmap?.let {
                    Image(it, contentDescription = packId, modifier = Modifier.height(40.dp))
                } ?: run {
                    Text(packId)
                }
            }
        } else {
            Text(packId)
        }
    }
}


@Composable
fun PokeCardAmount(isLoggedIn: Boolean, isAmountLoading: Boolean, onChangeAmountOwned: (Int) -> Unit, amountOwned: Int) {
    if (isLoggedIn) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("Amount owned:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(
                enabled = !isAmountLoading,
                onClick = { onChangeAmountOwned(amountOwned - 1) }) {
                Text("-")
            }
            Text(
                amountOwned.toString(),
                modifier = Modifier.padding(8.dp)
            )
            OutlinedButton(
                enabled = !isAmountLoading,
                onClick = { onChangeAmountOwned(amountOwned + 1) }) {
                Text("+")
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeTextRow(
    key: String,
    stringValue: String? = null,
    imageValue: String? = null,
    imageAltText: String? = null
) {
    //Don't show if there's no data to show at all
    if (stringValue == null && imageValue == null) {
        return
    }
    //Prepare image
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(imageValue) {
        if (imageValue != null) {
            val bytes = Res.readBytes("files/card_symbols/${imageValue}")
            imageBitmap = bytes.decodeToImageBitmap()
        }
    }
    //Pretty frame thingy
    Card(
        modifier = Modifier
            .height(30.dp)
            .widthIn(350.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .border(1.dp, Color.Gray)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            //Key
            Text(key, modifier = Modifier.width(100.dp).fillMaxHeight().background(Color.LightGray).padding(start = 8.dp))
            //Value (text and image are both optional; But we expect at least one of them)
            Row(modifier = Modifier.weight(1f).padding(start = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                imageBitmap?.let {
                    Image(it, contentDescription = imageAltText, modifier = Modifier.height(20.dp))
                } ?: run {
                    Text(imageAltText ?: "")
                }
                Text(stringValue ?: "")
            }
        }
    }
}