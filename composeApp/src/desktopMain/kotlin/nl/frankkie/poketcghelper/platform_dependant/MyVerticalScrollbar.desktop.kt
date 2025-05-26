package nl.frankkie.poketcghelper.platform_dependant

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun MyVerticalScrollbar(modifier: Modifier, state: LazyGridState) {
    VerticalScrollbar(
        modifier = modifier,
        adapter = rememberScrollbarAdapter(state),
    )
}