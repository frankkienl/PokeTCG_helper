package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
    cardSets: List<PokeCardSet>,
    cardFilter: PokeCardFilter,
    onCardClick: (PokeCardSet, PokeCard) -> Unit
) {
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
            item(span = { GridItemSpan(5) }) {
                //Cardset logo
                var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
                LaunchedEffect(cardSet) {
                    val bytes = Res.readBytes("files/card_symbols/${cardSet.codeName}/${cardSet.imageUrl}")
                    imageBitmap = bytes.decodeToImageBitmap()
                }
                if (imageBitmap == null) {
                    Text(cardSet.displayName)
                } else {
                    imageBitmap?.let { Image(it, contentDescription = cardSet.displayName, modifier = Modifier.padding(8.dp)) }
                }
            }
            //Cards
            val filteredCards = cardSet.cards.filter { someCard ->
                matchesCardFilter(someCard, cardFilter)
            }
            items(filteredCards) { card ->
                val ownedCard = appState.ownedCards.find { card.number == it.pokeCard.number }
                val isOwned = ownedCard != null && ownedCard.amount > 0
                PokeCardComposable(
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

fun matchesCardFilter(card: PokeCard, cardFilter: PokeCardFilter): Boolean {
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