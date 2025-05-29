package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import coil3.compose.AsyncImage
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeExpansion
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.resources.painterResource
import poketcg_helper.composeapp.generated.resources.Res
import poketcg_helper.composeapp.generated.resources.card_back

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SimpleImage(modifier: Modifier, imagePath: String, contentDescription: String? = null) {
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(imagePath) {
        val bytes = Res.readBytes(imagePath)
        imageBitmap = bytes.decodeToImageBitmap()
    }
    imageBitmap?.let {
        Image(it, contentDescription = contentDescription, modifier = modifier)
    }
}

@Composable
fun SimpleAsyncImage(modifier: Modifier, imagePath: String, contentDescription: String? = null) {
    val baseUrl = "http://frankkie.nl/poketcg/"
    AsyncImage(
        model = baseUrl+imagePath,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SimplePokeCardImage(
    pokeCard: PokeCard,
    pokeExpansion: PokeExpansion,
    cardPlaceholderImage: ImageBitmap?,
    showBlurred: Boolean = false,
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
            if (showBlurred) {
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
}

@Composable
fun SimpleAsyncPokeCardImage(
    modifier: Modifier = Modifier.height(100.dp),
    pokeCard: PokeCard,
    pokeExpansion: PokeExpansion,
    cardPlaceholderImage: ImageBitmap?,
    showBlurred: Boolean = false,
) {
    val baseUrl = "https://frankkie.nl/poketcg/"
    AsyncImage(
        modifier = modifier,
        model = baseUrl + "files/expansions/${pokeExpansion.symbol}/card_images/${pokeCard.imageUrl}",
        contentDescription = null,
        placeholder = painterResource(Res.drawable.card_back),
        colorFilter = if (showBlurred) ColorFilter.tint(Color(0xCCFFFFFF), blendMode = BlendMode.Color) else null
    )
}