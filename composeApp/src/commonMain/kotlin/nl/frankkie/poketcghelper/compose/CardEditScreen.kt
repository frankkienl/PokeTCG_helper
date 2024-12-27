package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeCardSet

/*
 * Semi-hidden feature; Edit cards
 */
@Composable
fun CardEditScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    ogCardSetCodeName: String,
    ogCardNumber: Int
) {
    val appState = appViewModel.appState.collectAsState().value
    val ogCardSet = appState.cardSets.find { someSet -> someSet.codeName == ogCardSetCodeName }?: return
    val ogCard = ogCardSet.cards.find { someCard -> someCard.number == ogCardNumber } ?: return
    var newCard by remember { mutableStateOf<PokeCard>(ogCard.copy()) }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Poke TCG Helper - Edit card") },
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() },
                    content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") })
            },
        )
    }) {
        Column(modifier = Modifier.scrollable(rememberScrollState(), Orientation.Vertical)) {
            Text("Edit card")
            SimpleEditableRow("number", ogCard.number.toString(), newCard.number.toString(), { val newInt = it?.toIntOrNull() ?: ogCard.number;newCard = newCard.copy(number = newInt) })
            SimpleEditableRow("pokeName", ogCard.pokeName, newCard.pokeName, { newCard = newCard.copy(pokeName = it ?: "") })
            SimpleEditableRow("imageUrl", ogCard.imageUrl, newCard.imageUrl, { newCard = newCard.copy(imageUrl = it ?: "") })
            SimpleEditableRow("pokeStage", ogCard.pokeStage.toString(), newCard.pokeStage.toString(), { val newInt = it?.toIntOrNull() ?: ogCard.pokeStage;newCard = newCard.copy(pokeStage = newInt) })
            SimpleEditableRow("pokeEvolvesFrom", ogCard.pokeEvolvesFrom, newCard.pokeEvolvesFrom, { newCard = newCard.copy(pokeEvolvesFrom = it) })
            SimpleEditableRow("pokeHp", ogCard.pokeHp.toString(), newCard.pokeHp.toString(), { val newInt = it?.toIntOrNull() ?: ogCard.pokeHp;newCard = newCard.copy(pokeHp = newInt) })
            SimpleEditableRow("pokeType", ogCard.pokeType, newCard.pokeType, { newCard = newCard.copy(pokeType = it) })
            SimpleEditableRow("pokeDesc", ogCard.pokeDesc, newCard.pokeDesc, { newCard = newCard.copy(pokeDesc = it) })
            SimpleEditableRow("pokeWeakness", ogCard.pokeWeakness, newCard.pokeWeakness, { newCard = newCard.copy(pokeWeakness = it) })
            SimpleEditableRow("pokeRetreat", ogCard.pokeRetreat.toString(), newCard.pokeRetreat.toString(), { val newInt = it?.toIntOrNull() ?: ogCard.pokeRetreat;newCard = newCard.copy(pokeRetreat = newInt) })
            SimpleEditableRow("pokeIllustrator", ogCard.pokeIllustrator, newCard.pokeIllustrator, { newCard = newCard.copy(pokeIllustrator = it) })
            SimpleEditableRow("pokeRarity", ogCard.pokeRarity, newCard.pokeRarity, { newCard = newCard.copy(pokeRarity = it) })
            SimpleEditableRow("pokeFlavour", ogCard.pokeFlavour, newCard.pokeFlavour, { newCard = newCard.copy(pokeFlavour = it) })
            SimpleEditableRow("pokePrint", ogCard.pokePrint, newCard.pokePrint, { newCard = newCard.copy(pokePrint = it) })

            Button(onClick = {
                println(newCard.toJsonString())
            }) {
                Text("Print JSON to STDOUT")
            }

            Spacer(modifier = Modifier.height(16.dp))
            SelectionContainer {
                Text(newCard.toJsonString())
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SimpleEditableRow(key: String, ogValue: String?, newValue: String?, onValueChange: (String?) -> Unit) {
    Card(
        modifier = Modifier
            .height(30.dp)
            .widthIn(350.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .border(1.dp, Color.Gray)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            //Key
            Text(key, modifier = Modifier.width(150.dp).fillMaxHeight().background(Color.LightGray).padding(start = 8.dp))
            //OgValue
            Text(ogValue ?: "null", modifier = Modifier.width(150.dp).fillMaxHeight())
            //Value (text and image are both optional; But we expect at least one of them)
            Row {
                Checkbox(checked = newValue == null, onCheckedChange = { newValue ->
                    if (newValue) {
                        onValueChange(null)
                    } else {
                        onValueChange("")
                    }
                })
                BasicTextField(newValue ?: "", onValueChange)
            }
        }
    }
}