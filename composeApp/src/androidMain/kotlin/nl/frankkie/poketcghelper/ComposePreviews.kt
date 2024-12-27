package nl.frankkie.poketcghelper

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import nl.frankkie.poketcghelper.compose.pokecard_parts.PokeTextRow
import nl.frankkie.poketcghelper.model.PokeType

@Preview
@Composable
fun PokeTextRowPreview() {
    MaterialTheme {
        Column {
            PokeTextRow("Pokémon", "Basic")
            PokeTextRow("Type", "", PokeType.GRASS.imageUrl)
            PokeTextRow("HP", "70")
            PokeTextRow("Weakness", "+20", PokeType.FIRE.imageUrl)
            PokeTextRow("Retreat", "x2", PokeType.FIRE.imageUrl)
        }
    }
}