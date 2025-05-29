package nl.frankkie.poketcghelper.compose.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.frankkie.poketcghelper.compose.SimpleAsyncPokeCardImage
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeExpansion
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun PokeCardComposableNormalMode(
    pokeExpansion: PokeExpansion,
    pokeCard: PokeCard,
    isLoggedIn: Boolean,
    isOwned: Boolean,
    cardPlaceholderImage: ImageBitmap? = null,
    onClick: (PokeExpansion, PokeCard) -> Unit,
    onLongClick: (PokeExpansion, PokeCard) -> Unit = { _, _ -> }
) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable {
                onClick(pokeExpansion, pokeCard)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleAsyncPokeCardImage(modifier = Modifier.height(100.dp), pokeCard, pokeExpansion, cardPlaceholderImage, showBlurred = !isOwned)
        Text(pokeCard.number.toString(), modifier = Modifier.combinedClickable(onClick = { onClick(pokeExpansion, pokeCard) }, onLongClick = { onLongClick(pokeExpansion, pokeCard) }))
        //Text(pokeCard.pokeName)
        Text(pokeCard.packId ?: "", fontSize = 10.sp)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeCardComposableAmountInputMode(
    pokeExpansion: PokeExpansion,
    pokeCard: PokeCard,
    amountOwned: Int,
    cardAmountLoading: Boolean,
    cardPlaceholderImage: ImageBitmap? = null,
    onChangeAmountOwned: (PokeExpansion, PokeCard, Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleAsyncPokeCardImage(modifier = Modifier.height(100.dp), pokeCard, pokeExpansion, cardPlaceholderImage, showBlurred = (amountOwned == 0 && !cardAmountLoading))
        Text(pokeCard.pokeName + " (${pokeCard.number})", fontSize = 10.sp)
        val useVertical = true
        if (!useVertical) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(
                    enabled = !cardAmountLoading,
                    onClick = {
                        var newAmount = amountOwned - 1
                        if (newAmount < 0) {
                            newAmount = 0
                        } // prevent negative numbers
                        onChangeAmountOwned(pokeExpansion, pokeCard, newAmount)
                    }) {
                    Text("-")
                }
                Text(
                    amountOwned.toString(),
                    modifier = Modifier.padding(4.dp)
                )
                OutlinedButton(
                    enabled = !cardAmountLoading,
                    onClick = { onChangeAmountOwned(pokeExpansion, pokeCard, amountOwned + 1) }) {
                    Text("+")
                }
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedButton(
                    enabled = !cardAmountLoading,
                    onClick = { onChangeAmountOwned(pokeExpansion, pokeCard, amountOwned + 1) }) {
                    Text("+")
                }
                Text(
                    amountOwned.toString()
                )
                OutlinedButton(
                    enabled = !cardAmountLoading,
                    onClick = {
                        var newAmount = amountOwned - 1
                        if (newAmount < 0) {
                            newAmount = 0
                        } // prevent negative numbers
                        onChangeAmountOwned(pokeExpansion, pokeCard, newAmount)
                    }) {
                    Text("-")
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PokeCardForFriendDetailScreen(pokeExpansion: PokeExpansion, pokeCard: PokeCard, cardPlaceholderImage: ImageBitmap? = null) {
    Column(
        modifier = Modifier.padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleAsyncPokeCardImage(modifier = Modifier.height(100.dp),
            pokeCard, pokeExpansion, cardPlaceholderImage, showBlurred = false)
        Text(pokeCard.pokeName + " (${pokeCard.number})", fontSize = 10.sp)
    }
}