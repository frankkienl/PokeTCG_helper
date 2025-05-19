package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import nl.frankkie.poketcghelper.AppState
import nl.frankkie.poketcghelper.compose.MyHorizontalDivider
import nl.frankkie.poketcghelper.compose.pokecard_parts.PokeRarityComposable
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeExpansion
import nl.frankkie.poketcghelper.model.PokeRarity
import nl.frankkie.poketcghelper.model.PokeType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res
import kotlin.math.exp

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GridOfCards(
    appState: AppState,
    homeScreenViewModel: HomeScreenViewModel,
    filteredCards: Map<PokeExpansion, List<PokeCard>>,
    onCardClick: (PokeExpansion, PokeCard) -> Unit,
    onCardLongClick: (PokeExpansion, PokeCard) -> Unit = { _, _ -> },
    cardAmountLoading: Boolean,
    onChangeAmountOwned: (PokeExpansion, PokeCard, Int) -> Unit,
) {
    val homeScreenUiState = homeScreenViewModel.uiState.collectAsState().value
    val expansions = appState.pokeExpansions
    val isLoggedIn = appState.supabaseUserInfo != null
    //Placeholder image
    var placeHolderImage by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(expansions) {
        val bytes = Res.readBytes("files/card_symbols/card_back.png")
        placeHolderImage = bytes.decodeToImageBitmap()
    }

    //Grid of cards
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(5),
    ) {
        if (homeScreenUiState.friendEmail != null) {
            item(span = { GridItemSpan(5) }) {
                Box(modifier = Modifier.padding(8.dp), contentAlignment = Alignment.Center) {
                    Text("Comparing cards with ${homeScreenUiState.friendEmail}", style = MaterialTheme.typography.h6)
                }
            }
        }
        expansions.forEach { expansion ->
            if (!homeScreenUiState.cardFilter.expansions.isEmpty() && !homeScreenUiState.cardFilter.expansions.contains(expansion)) {
                return@forEach
            }
            val filteredCardsForExpansion = filteredCards[expansion] ?: emptyList()
            if (filteredCardsForExpansion.isNotEmpty()) {
                //No need to show header, if there's no cards shown in this set.
                item(span = { GridItemSpan(5) }) {
                    //Cardset logo
                    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
                    LaunchedEffect(expansion) {
                        val bytes = Res.readBytes("files/expansions/${expansion.symbol}/expansion_symbols/${expansion.imageUrl}")
                        try {
                            imageBitmap = bytes.decodeToImageBitmap()
                        } catch (e: Exception) {
                            println("GridOfCards: Error reading CardSet logo image")
                            e.printStackTrace()
                            imageBitmap = null
                        }
                    }
                    Box(modifier = Modifier.height(80.dp), contentAlignment = Alignment.Center) {
                        if (imageBitmap == null) {
                            Text(expansion.displayName)
                        } else {
                            imageBitmap?.let { Image(it, contentDescription = expansion.displayName, modifier = Modifier.padding(8.dp)) }
                        }
                    }
                }
            }
            items(filteredCardsForExpansion) { card ->
                val ownedCard = appState.ownedCards.find { (card.number == it.pokeCard.number && expansion.codeName == it.pokeExpansion.codeName) }
                val isOwned = ownedCard != null && ownedCard.amount > 0
                val amountOwned = ownedCard?.amount ?: 0
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (homeScreenUiState.friendUid != null) {
                        val friendAmountOwned = homeScreenUiState.friendOwnedCards.find { (card.number == it.card_number && expansion.codeName == it.card_set_id) }?.card_amount ?: 0
                        val arrow = if (amountOwned == 0 && friendAmountOwned > 1) {
                            "⬅\uFE0F"
                        } else if (amountOwned > 1 && friendAmountOwned == 0) {
                            "➡\uFE0F"
                        } else {
                            ""
                        }
                        MyHorizontalDivider()
                        Text("$arrow $amountOwned / $friendAmountOwned $arrow")
                        //if (arrow.isNotEmpty()) {
                        PokeRarityComposable(card.pokeRarity)
                        //}
                    }
                    if (homeScreenUiState.amountInputMode && isLoggedIn) {
                        PokeCardComposableAmountInputMode(
                            pokeExpansion = expansion,
                            pokeCard = card,
                            amountOwned = amountOwned,
                            cardAmountLoading = cardAmountLoading,
                            cardPlaceholderImage = placeHolderImage,
                            onChangeAmountOwned = onChangeAmountOwned,
                        )
                    } else {
                        PokeCardComposableNormalMode(
                            pokeExpansion = expansion,
                            pokeCard = card,
                            isLoggedIn = appState.supabaseUserInfo != null,
                            isOwned = isOwned,
                            cardPlaceholderImage = placeHolderImage,
                            onClick = { _set, _card ->
                                onCardClick(_set, _card)
                            },
                            onLongClick = { _set, _card -> onCardLongClick(_set, _card) }
                        )
                    }
                }
            }
        }
    }
}

fun matchesCardFilter(
    card: PokeCard,
    expansion: PokeExpansion,
    cardFilter: PokeCardFilter,
    appState: AppState
): Boolean {
    //SearchQuery
    if (cardFilter.searchQuery.isNotBlank()) {
        if (!card.pokeName.contains(cardFilter.searchQuery, ignoreCase = true)) {
            return false
        }
    }
    //ExpansionPack selected
    if (cardFilter.expansionPacks.isNotEmpty()) {
        if (card.packId != null) { //when null, the card is not part of a pack (like promo-a)
            val cardPack = expansion.packs.find { it.id == card.packId }
            if (!cardFilter.expansionPacks.contains(cardPack)) {
                return false
            }
        }
    }
    //Owned
    if (cardFilter.ownedStatus.isNotEmpty()) {
        val ownedCard = appState.ownedCards.find { (card.number == it.pokeCard.number && expansion.codeName == it.pokeExpansion.codeName) }
        val isOwned = ownedCard != null && ownedCard.amount > 0
        //val amountOwned = ownedCard?.amount ?: 0
        if (!cardFilter.ownedStatus.contains(isOwned)) {
            return false
        }
    }
    //Rarity
    if (cardFilter.rarities.isNotEmpty()) {
        val rarity = PokeRarity.valueOf(card.pokeRarity ?: "UNKNOWN")
        if (!cardFilter.rarities.contains(rarity)) {
            return false
        }
    }
    //Types
    if (cardFilter.types.isNotEmpty()) {
        val type = PokeType.valueOf(card.pokeType ?: "UNKNOWN")
        if (!cardFilter.types.contains(type)) {
            return false
        }
    }
    return true
}