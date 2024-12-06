package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import nl.frankkie.poketcghelper.AppState
import nl.frankkie.poketcghelper.model.PokeRarity
import nl.frankkie.poketcghelper.model.PokeType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

data class PokeCardFilter(
    val types: List<PokeType> = emptyList(),
    val rarities: List<PokeRarity> = emptyList(),
    val flairs: List<String> = emptyList(),
    val packs: List<String> = emptyList(),
    val ownedStatus: List<Boolean> = emptyList(),
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokeFilterDialog(homeScreenViewModel: HomeScreenViewModel, appState: AppState) {
    val homeScreenUiState = homeScreenViewModel.uiState.collectAsState().value
    Dialog(
        onDismissRequest = { homeScreenViewModel.hideFilterDialog() },
    ) {
        Card(modifier = Modifier.padding(8.dp).widthIn(250.dp), shape = RoundedCornerShape(8.dp)) {
            val scrollState = rememberScrollState()
            Column(modifier = Modifier.padding(8.dp).scrollable(scrollState, orientation = Orientation.Vertical)) {
                if (appState.supabaseUserInfo!=null) {
                    Text("Owned")
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        buildFilterOwned(homeScreenUiState, homeScreenViewModel, true)
                        buildFilterOwned(homeScreenUiState, homeScreenViewModel, false)
                    }
                }
                Text("Rarity")
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.D1)
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.D2)
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.D3)
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.D4)
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.S1)
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.S2)
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.S3)
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.C)
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.PROMO)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Type")
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.GRASS)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.FIRE)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.WATER)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.LIGHTNING)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.PSYCHIC)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.FIGHTING)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.DARKNESS)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.METAL)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.DRAGON)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.COLORLESS)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.ITEM)
                    buildFilterType(homeScreenUiState, homeScreenViewModel, PokeType.SUPPORT)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
fun buildFilterRarity(homeScreenUiState: HomeScreenUiState, homeScreenViewModel: HomeScreenViewModel, pokeRarity: PokeRarity) {
    val selected = homeScreenUiState.cardFilter.rarities.contains(pokeRarity)
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeRarity.imageUrl) {
        if (pokeRarity.imageUrl != null) {
            val bytes = Res.readBytes("files/card_symbols/${pokeRarity.imageUrl}")
            imageBitmap = bytes.decodeToImageBitmap()
        }
    }
    FilterChip(
        selected = selected,
        onClick = { clickedFilterRarity(homeScreenViewModel, pokeRarity) },
        content = {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                if (imageBitmap != null) {
                    imageBitmap?.let { safeBitmap ->
                        repeat(pokeRarity.symbolCount ?: 0) {
                            Image(safeBitmap, contentDescription = pokeRarity.displayName)
                        }
                    }
                } else {
                    Text(pokeRarity.displayName)
                }
            }
        },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Selected icon",
                    modifier = Modifier.size(16.dp)
                )
            }
        } else {
            null
        },
    )
}

fun clickedFilterRarity(homeScreenViewModel: HomeScreenViewModel, pokeRarity: PokeRarity) {
    homeScreenViewModel.uiState.value.cardFilter.let {
        val newFilterType = if (it.rarities.contains(pokeRarity)) {
            it.copy(rarities = it.rarities - pokeRarity)
        } else {
            it.copy(rarities = it.rarities + pokeRarity)
        }
        homeScreenViewModel.setCardFilter(newFilterType)
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
fun buildFilterType(homeScreenUiState: HomeScreenUiState, homeScreenViewModel: HomeScreenViewModel, pokeType: PokeType) {
    val selected = homeScreenUiState.cardFilter.types.contains(pokeType)

    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(pokeType) {
        if (pokeType.imageUrl != null) {
            val bytes = Res.readBytes("files/card_symbols/${pokeType.imageUrl}")
            imageBitmap = bytes.decodeToImageBitmap()
        }
    }
    FilterChip(
        selected = selected,
        onClick = { clickedFilterType(homeScreenViewModel, pokeType) },
        content = {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(pokeType.displayName)
                imageBitmap?.let {
                    Image(
                        it,
                        contentScale = ContentScale.FillHeight,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Selected icon",
                    modifier = Modifier.size(16.dp)
                )
            }
        } else {
            null
        },
    )
}

fun clickedFilterType(homeScreenViewModel: HomeScreenViewModel, pokeType: PokeType) {
    homeScreenViewModel.uiState.value.cardFilter.let {
        val newFilterType = if (it.types.contains(pokeType)) {
            it.copy(types = it.types - pokeType)
        } else {
            it.copy(types = it.types + pokeType)
        }
        homeScreenViewModel.setCardFilter(newFilterType)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun buildFilterOwned(homeScreenUiState: HomeScreenUiState, homeScreenViewModel: HomeScreenViewModel, isOwned: Boolean) {
    val selected = homeScreenUiState.cardFilter.ownedStatus.contains(isOwned)
    FilterChip(
        selected = selected,
        onClick = { clickedFilterOwned(homeScreenViewModel, isOwned) },
        content = { Text(if (isOwned) "Owned" else "Not owned") },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Selected icon",
                    modifier = Modifier.size(16.dp)
                )
            }
        } else {
            null
        },
    )
}

fun clickedFilterOwned(homeScreenViewModel: HomeScreenViewModel, isOwned: Boolean) {
    homeScreenViewModel.uiState.value.cardFilter.let {
        val newFilterOwned = if (it.ownedStatus.contains(isOwned)) {
            it.copy(ownedStatus = it.ownedStatus - isOwned)
        } else {
            it.copy(ownedStatus = it.ownedStatus + isOwned)
        }
        homeScreenViewModel.setCardFilter(newFilterOwned)
    }
}