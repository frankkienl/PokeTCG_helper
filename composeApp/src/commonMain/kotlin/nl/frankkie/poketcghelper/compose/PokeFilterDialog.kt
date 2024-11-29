package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import nl.frankkie.poketcghelper.model.PokeType

data class PokeCardFilter(
    val types: List<String> = emptyList(),
    val rarities: List<String> = emptyList(),
    val flairs: List<String> = emptyList(),
    val packs: List<String> = emptyList()
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokeFilterDialog(homeScreenViewModel: HomeScreenViewModel) {
    val homeScreenUiState = homeScreenViewModel.uiState.collectAsState().value
    Dialog(
        onDismissRequest = { homeScreenViewModel.hideFilterDialog() },
    ) {
        Card(modifier = Modifier.padding(8.dp).widthIn(250.dp), shape = RoundedCornerShape(8.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Type")
                FlowRow (horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.DARKNESS)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.DRAGON)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.FIGHTING)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.FIRE)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.GRASS)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.LIGHTNING)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.METAL)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.NORMAL)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.PSYCHIC)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.WATER)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.ITEM)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.SUPPORT)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun buildFilterType(homeScreenUiState: HomeScreenUiState, homeScreenViewModel: HomeScreenViewModel, pokeType: PokeType) {
    var selected = false
    homeScreenUiState.cardFilter?.let {
        selected = it.types.contains(pokeType.codeName)
    }
    FilterChip(
        onClick = { clickedFilterType(homeScreenViewModel, pokeType) },
        content = { Row{ Text(pokeType.displayName) /* TODO: icon */} },
        selected = selected,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(16.dp)
                )
            }
        } else {
            null
        },
    )
}

fun clickedFilterType(homeScreenViewModel: HomeScreenViewModel, pokeType: PokeType) {
    if (homeScreenViewModel.uiState.value.cardFilter == null) {
        val newFilterType = listOf(pokeType.codeName)
        val cardFilter = PokeCardFilter(
            types = newFilterType
        )
        homeScreenViewModel.setCardFilter(cardFilter)
    } else {
        homeScreenViewModel.uiState.value.cardFilter?.let {
            val newFilterType = if (it.types.contains(pokeType.codeName)) {
                it.copy(types = it.types - pokeType.codeName)
            } else {
                it.copy(types = it.types + pokeType.codeName)
            }
            homeScreenViewModel.setCardFilter(newFilterType)
        }
    }
}