package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import nl.frankkie.poketcghelper.AppState
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeCardSet
import nl.frankkie.poketcghelper.model.PokeRarity
import nl.frankkie.poketcghelper.model.PokeType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GridOfCards(
    appState: AppState,
    homeScreenViewModel: HomeScreenViewModel,
    filteredCards: Map<PokeCardSet, List<PokeCard>>,
    onCardClick: (PokeCardSet, PokeCard) -> Unit,
    cardAmountLoading: Boolean,
    onChangeAmountOwned: (PokeCardSet, PokeCard, Int) -> Unit,
) {
    val homeScreenUiState = homeScreenViewModel.uiState.collectAsState().value
    val cardSets = appState.cardSets
    val isLoggedIn = appState.supabaseUserInfo != null
    //Placeholder image
    var placeHolderImage by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(cardSets) {
        val bytes = Res.readBytes("files/card_symbols/card_back.png")
        placeHolderImage = bytes.decodeToImageBitmap()
    }

    //Grid of cards
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(5),
    ) {

        cardSets.forEach { cardSet ->
            val filteredCardsForCardSet = filteredCards[cardSet] ?: emptyList()
            if (filteredCardsForCardSet.isNotEmpty()) {
                //No need to show header, if there's no cards shown in this set.
                item(span = { GridItemSpan(5) }) {
                    //Cardset logo
                    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
                    LaunchedEffect(cardSet) {
                        val bytes = Res.readBytes("files/card_symbols/${cardSet.codeName}/${cardSet.imageUrl}")
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
                            Text(cardSet.displayName)
                        } else {
                            imageBitmap?.let { Image(it, contentDescription = cardSet.displayName, modifier = Modifier.padding(8.dp)) }
                        }
                    }
                }
            }
            items(filteredCardsForCardSet) { card ->
                val ownedCard = appState.ownedCards.find { (card.number == it.pokeCard.number && cardSet.codeName == it.pokeCardSet.codeName) }
                val isOwned = ownedCard != null && ownedCard.amount > 0
                val amountOwned = ownedCard?.amount ?: 0
                if (homeScreenUiState.amountInputMode && isLoggedIn) {
                    PokeCardComposableAmountInputMode(
                        cardSet = cardSet,
                        pokeCard = card,
                        amountOwned = amountOwned,
                        cardAmountLoading = cardAmountLoading,
                        cardPlaceholderImage = placeHolderImage,
                        onChangeAmountOwned = onChangeAmountOwned,
                    )
                } else {
                    PokeCardComposableNormalMode(
                        cardSet = cardSet,
                        pokeCard = card,
                        isLoggedIn = appState.supabaseUserInfo != null,
                        isOwned = isOwned,
                        cardPlaceholderImage = placeHolderImage,
                        onClick = { _set, _card ->
                            onCardClick(_set, _card)
                        }
                    )
                }
            }
        }
    }
}

fun matchesCardFilter(card: PokeCard, cardSet: PokeCardSet, cardFilter: PokeCardFilter, appState: AppState): Boolean {
    //SearchQuery
    if (cardFilter.searchQuery.isNotBlank()) {
        if (!card.pokeName.contains(cardFilter.searchQuery, ignoreCase = true)) {
            return false
        }
    }
    //Owned
    if (cardFilter.ownedStatus.isNotEmpty()) {
        val ownedCard = appState.ownedCards.find { (card.number == it.pokeCard.number && cardSet.codeName == it.pokeCardSet.codeName) }
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