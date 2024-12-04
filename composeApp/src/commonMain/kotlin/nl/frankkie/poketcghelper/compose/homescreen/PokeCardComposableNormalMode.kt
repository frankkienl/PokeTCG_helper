package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.frankkie.poketcghelper.model.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeCardComposableNormalMode(
    cardSet: PokeCardSet,
    pokeCard: PokeCard,
    isLoggedIn: Boolean,
    isOwned: Boolean,
    cardPlaceholderImage: ImageBitmap? = null,
    onClick: (PokeCardSet, PokeCard) -> Unit
) {
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeCard) {
        val bytes = Res.readBytes("files/card_images/${cardSet.codeName}/${pokeCard.imageUrl}")
        imageBitmap = bytes.decodeToImageBitmap()
    }
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable {
                onClick(cardSet, pokeCard)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.heightIn(50.dp, 150.dp)) {
            if (imageBitmap == null) {
                cardPlaceholderImage?.let {
                    Image(it, contentDescription = "Loading ...")
                }
                Box(Modifier.padding(top = 10.dp).align(Alignment.Center)) {
                    Text(
                        "Loading ...",
                        modifier = Modifier.fillMaxHeight().align(Alignment.Center),
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Text(
                        "Loading ...",
                        modifier = Modifier.fillMaxHeight().align(Alignment.Center),
                        style = TextStyle.Default.copy(
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            drawStyle = Stroke(
                                miter = 10f,
                                width = 2f,
                                join = StrokeJoin.Round
                            )
                        )
                    )
                }
            }
            imageBitmap?.let {
                if (isLoggedIn && !isOwned) {
                    //Show blurred
                    Image(
                        it, null, colorFilter = ColorFilter.tint(Color(0xCCFFFFFF), blendMode = BlendMode.Color),
                    )
                } else {
                    //Show normally
                    Image(it, null)
                }
            }
        }
        Text(pokeCard.number.toString())
        //Text(pokeCard.pokeName)
        Text(pokeCard.packId ?: "", fontSize = 10.sp)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeCardComposableAmountInputMode(
    cardSet: PokeCardSet,
    pokeCard: PokeCard,
    amountOwned: Int,
    cardAmountLoading: Boolean,
    cardPlaceholderImage: ImageBitmap? = null,
    onChangeAmountOwned: (PokeCardSet, PokeCard, Int) -> Unit
) {
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeCard) {
        val bytes = Res.readBytes("files/card_images/${cardSet.codeName}/${pokeCard.imageUrl}")
        imageBitmap = bytes.decodeToImageBitmap()
    }
    Column(
        modifier = Modifier.padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.heightIn(50.dp, 150.dp)) {
            if (imageBitmap == null) {
                cardPlaceholderImage?.let {
                    Image(it, contentDescription = "Loading ...")
                }
                Box(Modifier.padding(top = 10.dp).align(Alignment.Center)) {
                    Text(
                        "Loading ...",
                        modifier = Modifier.fillMaxHeight().align(Alignment.Center),
                        style = TextStyle.Default.copy(
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Text(
                        "Loading ...",
                        modifier = Modifier.fillMaxHeight().align(Alignment.Center),
                        style = TextStyle.Default.copy(
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            drawStyle = Stroke(
                                miter = 10f,
                                width = 2f,
                                join = StrokeJoin.Round
                            )
                        )
                    )
                }
            }
            imageBitmap?.let {
                if (amountOwned < 1) {
                    //Show blurred
                    Image(
                        it, null, colorFilter = ColorFilter.tint(Color(0xCCFFFFFF), blendMode = BlendMode.Color),
                    )
                } else {
                    //Show normally
                    Image(it, null)
                }
            }
        }
        //Text(pokeCard.number.toString())
        Text(pokeCard.pokeName, fontSize = 10.sp)
        val useVertical = true
        if (!useVertical) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(
                    enabled = !cardAmountLoading,
                    onClick = {
                        var newAmount = amountOwned - 1
                        if (newAmount < 0) {
                            newAmount = 0
                        } // prevent negative numbers
                        onChangeAmountOwned(cardSet, pokeCard, newAmount)
                    }) {
                    Text("-")
                }
                Text(
                    amountOwned.toString(),
                    modifier = Modifier.padding(4.dp)
                )
                OutlinedButton(
                    enabled = !cardAmountLoading,
                    onClick = { onChangeAmountOwned(cardSet, pokeCard, amountOwned + 1) }) {
                    Text("+")
                }
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedButton(
                    enabled = !cardAmountLoading,
                    onClick = { onChangeAmountOwned(cardSet, pokeCard, amountOwned + 1) }) {
                    Text("+")
                }
                Text(
                    amountOwned.toString(),
                    modifier = Modifier.padding(4.dp)
                )
                OutlinedButton(
                    enabled = !cardAmountLoading,
                    onClick = {
                        var newAmount = amountOwned - 1
                        if (newAmount < 0) {
                            newAmount = 0
                        } // prevent negative numbers
                        onChangeAmountOwned(cardSet, pokeCard, newAmount)
                    }) {
                    Text("-")
                }
            }
        }
        //Text(pokeCard.packId ?: "", fontSize = 10.sp)
    }
}
