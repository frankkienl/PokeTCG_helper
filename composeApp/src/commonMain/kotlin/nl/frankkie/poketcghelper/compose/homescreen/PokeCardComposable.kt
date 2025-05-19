package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun PokeCardComposableNormalMode(
    pokeExpansion: PokeExpansion,
    pokeCard: PokeCard,
    isLoggedIn: Boolean,
    isOwned: Boolean,
    cardPlaceholderImage: ImageBitmap? = null,
    onClick: (PokeExpansion, PokeCard) -> Unit,
    onLongClick: (PokeExpansion, PokeCard) -> Unit = { _, _ -> }
) {
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeCard) {
        try {
            // Try small version
            val bytes = Res.readBytes("files/expansions/${pokeExpansion.symbol}/card_images_small/${pokeCard.imageUrl}")
            imageBitmap = bytes.decodeToImageBitmap()
        } catch (e: Exception) {
            println("PokeCardComposableNormalMode: Failed to load image (small) " + e.message)
            try {
                // Try large version
                val bytes = Res.readBytes("files/expansions/${pokeExpansion.symbol}/card_images/${pokeCard.imageUrl}")
                imageBitmap = bytes.decodeToImageBitmap()
            } catch (e: Exception) {
                println("PokeCardComposableNormalMode: Failed to load image (large) " + e.message)
                // Image doesn't work.
                imageBitmap = null
            }
        }
    }
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable {
                onClick(pokeExpansion, pokeCard)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.height(100.dp)) {
            //if cards image is not loaded yet, show placeholder image instead
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
                    //Show blurred, card is not in collection
                    Image(it, null, colorFilter = ColorFilter.tint(Color(0xCCFFFFFF), blendMode = BlendMode.Color))
                } else {
                    //Show normally
                    Image(it, null)
                }
            }
        }
        Text(pokeCard.number.toString(), modifier = Modifier.combinedClickable(onClick = { onClick(pokeExpansion, pokeCard) }, onLongClick = { onLongClick(pokeExpansion, pokeCard) }))
        //Text(pokeCard.pokeName)
        Text(pokeCard.packId ?: "", fontSize = 10.sp)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeCardComposableAmountInputMode(
    pokeExpansion: PokeExpansion,
    pokeCard: PokeCard,
    amountOwned: Int,
    cardAmountLoading: Boolean,
    cardPlaceholderImage: ImageBitmap? = null,
    onChangeAmountOwned: (PokeExpansion, PokeCard, Int) -> Unit
) {
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeCard) {
        try {
            // Try small version
            val bytes = Res.readBytes("files/expansions/${pokeExpansion.symbol}/card_images_small/${pokeCard.imageUrl}")
            imageBitmap = bytes.decodeToImageBitmap()
        } catch (e: Exception) {
            println("PokeCardComposableAmountInputMode: Failed to load image (small) " + e.message)
            try {
                // Try large version
                val bytes = Res.readBytes("files/expansions/${pokeExpansion.symbol}/card_images/${pokeCard.imageUrl}")
                imageBitmap = bytes.decodeToImageBitmap()
            } catch (e: Exception) {
                println("PokeCardComposableAmountInputMode: Failed to load image (large) " + e.message)
                // Image doesn't work.
                imageBitmap = null
            }
        }
    }
    Column(
        modifier = Modifier.padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.height(100.dp)) {
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
        Text(pokeCard.pokeName + " (${pokeCard.number})", fontSize = 10.sp)
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
                        onChangeAmountOwned(pokeExpansion, pokeCard, newAmount)
                    }) {
                    Text("-")
                }
                Text(
                    amountOwned.toString(),
                    modifier = Modifier.padding(4.dp)
                )
                OutlinedButton(
                    enabled = !cardAmountLoading,
                    onClick = { onChangeAmountOwned(pokeExpansion, pokeCard, amountOwned + 1) }) {
                    Text("+")
                }
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedButton(
                    enabled = !cardAmountLoading,
                    onClick = { onChangeAmountOwned(pokeExpansion, pokeCard, amountOwned + 1) }) {
                    Text("+")
                }
                Text(
                    amountOwned.toString()
                )
                OutlinedButton(
                    enabled = !cardAmountLoading,
                    onClick = {
                        var newAmount = amountOwned - 1
                        if (newAmount < 0) {
                            newAmount = 0
                        } // prevent negative numbers
                        onChangeAmountOwned(pokeExpansion, pokeCard, newAmount)
                    }) {
                    Text("-")
                }
            }
        }
        //Text(pokeCard.packId ?: "", fontSize = 10.sp)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeCardForFriendDetailScreen(pokeExpansion: PokeExpansion, pokeCard: PokeCard, cardPlaceholderImage: ImageBitmap? = null) {
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeCard) {
        try {
            // Try small version
            val bytes = Res.readBytes("files/expansions/${pokeExpansion.symbol}/card_images_small/${pokeCard.imageUrl}")
            imageBitmap = bytes.decodeToImageBitmap()
        } catch (e: Exception) {
            println("PokeCardComposableAmountInputMode: Failed to load image (small) " + e.message)
            try {
                // Try large version
                val bytes = Res.readBytes("files/expansions/${pokeExpansion.symbol}/card_images/${pokeCard.imageUrl}")
                imageBitmap = bytes.decodeToImageBitmap()
            } catch (e: Exception) {
                println("PokeCardComposableAmountInputMode: Failed to load image (large) " + e.message)
                // Image doesn't work.
                imageBitmap = null
            }
        }
    }
    Column(
        modifier = Modifier.padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.height(100.dp)) {
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
                Image(it, null)
            }
        }
        Text(pokeCard.pokeName + " (${pokeCard.number})", fontSize = 10.sp)
    }
}