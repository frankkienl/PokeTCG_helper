package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import nl.frankkie.poketcghelper.model.PokeExpansion
import nl.frankkie.poketcghelper.model.PokeExpansionPack
import nl.frankkie.poketcghelper.model.PokeRarity
import nl.frankkie.poketcghelper.model.PokeType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

data class PokeCardFilter(
    val expansions: List<PokeExpansion> = emptyList(),
    val expansionPacks: List<PokeExpansionPack> = emptyList(),
    val searchQuery: String = "",
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
            Column(modifier = Modifier.padding(8.dp).verticalScroll(scrollState)) {
                Text("Search by name")
                TextField(homeScreenUiState.cardFilter.searchQuery, {
                    homeScreenViewModel.setCardFilter(homeScreenUiState.cardFilter.copy(searchQuery = it))
                })
                Spacer(Modifier.height(8.dp))

                Text("Filter by expansion")
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    appState.pokeExpansions.forEach { set ->
                        buildFilterExpansion2(homeScreenUiState, homeScreenViewModel, set)
                    }
                }

                if (appState.supabaseUserInfo != null) {
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
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.SHINY1)
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.SHINY2)
                    buildFilterRarity(homeScreenUiState, homeScreenViewModel, PokeRarity.PROMO)
                }
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

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
fun buildFilterExpansion2(homeScreenUiState: HomeScreenUiState, homeScreenViewModel: HomeScreenViewModel, expansion: PokeExpansion) {
    val expansionSelected = homeScreenUiState.cardFilter.expansions.contains(expansion)
    var imageBitmapExpansion by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(expansion) {
        val bytes = Res.readBytes("files/expansions/${expansion.symbol}/expansion_symbols/${expansion.imageUrl}")
        imageBitmapExpansion = bytes.decodeToImageBitmap()
    }
    FilterChip(
        selected = expansionSelected,
        onClick = { clickedFilterExpansion(homeScreenViewModel, expansion) },
        content = {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(expansion.displayName)
                imageBitmapExpansion?.let {
                    Image(
                        it,
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null,
                        modifier = Modifier.width(64.dp)
                    )
                }
            }
        },
        selectedIcon = if (expansionSelected) {
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

    if (expansionSelected) {
        // Show the expansion packs
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            expansion.packs.forEach { pack ->
                var imageBitmapPack by remember {
                    mutableStateOf<ImageBitmap?>(null)
                }
                LaunchedEffect(pack) {
                    val bytes = Res.readBytes("files/expansions/${expansion.symbol}/expansion_symbols/${pack.imageUrlSymbol}")
                    imageBitmapPack = bytes.decodeToImageBitmap()
                }
                val packSelected = homeScreenUiState.cardFilter.expansionPacks.contains(pack)
                FilterChip(
                    selected = packSelected,
                    selectedIcon = if (packSelected) {
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
                    onClick = {
                        val newFilterExpansionPack = if (homeScreenUiState.cardFilter.expansionPacks.contains(pack)) {
                            homeScreenUiState.cardFilter.copy(expansionPacks = homeScreenUiState.cardFilter.expansionPacks - pack)
                        } else {
                            homeScreenUiState.cardFilter.copy(expansionPacks = homeScreenUiState.cardFilter.expansionPacks + pack)
                        }
                        homeScreenViewModel.setCardFilter(newFilterExpansionPack)
                    },
                    content = {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(pack.name)
                            imageBitmapPack?.let {
                                Image(
                                    it,
                                    contentScale = ContentScale.FillWidth,
                                    contentDescription = null,
                                    modifier = Modifier.width(64.dp)
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}

fun clickedFilterExpansion(homeScreenViewModel: HomeScreenViewModel, expansion: PokeExpansion) {
    homeScreenViewModel.uiState.value.cardFilter.let {
        val newFilterExpansion = if (it.expansions.contains(expansion)) {
            it.copy(expansions = it.expansions - expansion)
        } else {
            it.copy(expansions = it.expansions + expansion)
        }
        homeScreenViewModel.setCardFilter(newFilterExpansion)
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