package nl.frankkie.poketcghelper.platform_dependant

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun MyVerticalScrollbar(
    modifier: Modifier = Modifier,
    state: LazyGridState
)