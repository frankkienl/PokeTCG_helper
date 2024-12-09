package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyHorizontalDivider() {
    //The HorizontalDivider() is apparently not available on Compose KMP?
    //This is close enough
    Spacer(Modifier
        .height(2.dp)
        .fillMaxWidth()
        .background(Color.LightGray)
        .padding(vertical = 16.dp)
    )
}