package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

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