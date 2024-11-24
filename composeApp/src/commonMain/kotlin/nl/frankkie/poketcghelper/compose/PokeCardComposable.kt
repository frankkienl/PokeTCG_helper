package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import nl.frankkie.poketcghelper.model.PokeCard
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeCardComposable(
    pokeCard: PokeCard,
    isLoggedIn: Boolean,
    isOwned: Boolean,
    onClick: (PokeCard) -> Unit
) {
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
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable { onClick(pokeCard) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageBitmap?.let {
            if (isLoggedIn && !isOwned) {
                //Show blurred
                Image(
                    it,
                    null,
                    colorFilter = ColorFilter.tint(Color.White)
                )
            } else {
                //Show normally
                Image(it, null)
            }
        }
        Text(pokeCard.number.toString())
        Text(pokeCard.pokeName)
        Text(pokeCard.packId ?: "")
    }
}