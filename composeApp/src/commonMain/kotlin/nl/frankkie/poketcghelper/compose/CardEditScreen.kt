package nl.frankkie.poketcghelper.compose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.frankkie.poketcghelper.AppViewModel
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeRarity
import nl.frankkie.poketcghelper.model.PokeType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.MissingResourceException
import org.jetbrains.compose.resources.decodeToImageBitmap
import poketcg_helper.composeapp.generated.resources.Res

/*
 * Semi-hidden feature; Edit cards
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun CardEditScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    ogExpansionCodeName: String,
    ogCardNumber: Int
) {
    val appState = appViewModel.appState.collectAsState().value
    val ogExpansion = appState.pokeExpansions.find { someExpansion -> someExpansion.codeName == ogExpansionCodeName } ?: return
    val ogCard = ogExpansion.cards.find { someCard -> someCard.number == ogCardNumber } ?: return
    var newCard by remember { mutableStateOf<PokeCard>(ogCard.copy()) }
    val cardHeight = 512.dp
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    LaunchedEffect(ogCard) {
        try {
            val bytes = Res.readBytes("files/expansions/${ogExpansion.symbol}/card_images/${ogCard.imageUrl}")
            imageBitmap = bytes.decodeToImageBitmap()
        } catch (missingResourceException: MissingResourceException) {
            println("PokeCardDialog: Failed to load image " + missingResourceException.message)
            //Ignore; No image it is.
        }
    }
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
        Row {
            Column {
                imageBitmap?.let {
                    Image(
                        it,
                        modifier = Modifier.height(cardHeight).padding(8.dp),
                        contentScale = ContentScale.Fit,
                        contentDescription = null
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Edit card")
                SimpleEditableRow("number", ogCard.number.toString(), newCard.number.toString(), { val newInt = it?.toIntOrNull() ?: ogCard.number;newCard = newCard.copy(number = newInt) })
                SimpleEditableRow("pokeName", ogCard.pokeName, newCard.pokeName, { newCard = newCard.copy(pokeName = it ?: "") })
                SimpleEditableRow("imageUrl", ogCard.imageUrl, newCard.imageUrl, { newCard = newCard.copy(imageUrl = it ?: "") })
                SimpleEditableRow("pokeStage", ogCard.pokeStage.toString(), newCard.pokeStage.toString(), { val newInt = it?.toIntOrNull() ?: ogCard.pokeStage;newCard = newCard.copy(pokeStage = newInt) })
                SimpleEditableRow("pokeEvolvesFrom", ogCard.pokeEvolvesFrom, newCard.pokeEvolvesFrom, { newCard = newCard.copy(pokeEvolvesFrom = it) })
                SimpleEditableRow("pokeHp", ogCard.pokeHp.toString(), newCard.pokeHp.toString(), { val newInt = it?.toIntOrNull() ?: ogCard.pokeHp;newCard = newCard.copy(pokeHp = newInt) })
                SpecificEditableRow("pokeType", ogCard.pokeType, newCard.pokeType, PokeType.entries.map { it.codeName }, { newCard = newCard.copy(pokeType = it) })
                SimpleEditableRow("pokeDesc", ogCard.pokeDesc, newCard.pokeDesc, { newCard = newCard.copy(pokeDesc = it) })
                SpecificEditableRow("pokeWeakness", ogCard.pokeWeakness, newCard.pokeWeakness, PokeType.entries.map { it.codeName }, { newCard = newCard.copy(pokeWeakness = it) })
                SimpleEditableRow("pokeRetreat", ogCard.pokeRetreat.toString(), newCard.pokeRetreat.toString(), { val newInt = it?.toIntOrNull() ?: ogCard.pokeRetreat;newCard = newCard.copy(pokeRetreat = newInt) })
                SimpleEditableRow("pokeIllustrator", ogCard.pokeIllustrator, newCard.pokeIllustrator, { newCard = newCard.copy(pokeIllustrator = it) })
                SpecificEditableRow("pokeRarity", ogCard.pokeRarity, newCard.pokeRarity, PokeRarity.entries.map { it.codeName }, { newCard = newCard.copy(pokeRarity = it) })
                SimpleEditableRow("pokeFlavour", ogCard.pokeFlavour, newCard.pokeFlavour, { newCard = newCard.copy(pokeFlavour = it) })
                SimpleEditableRow("pokePrint", ogCard.pokePrint, newCard.pokePrint, { newCard = newCard.copy(pokePrint = it) })

                Row {
                    Button(onClick = {
                        println(newCard.toJsonString())
                    }) {
                        Text("Print JSON to STDOUT")
                    }

                    Button(onClick = {
                        println(newCard.toJsonString())
                        navController.popBackStack()
                        navController.navigate(Routes.CardEditScreen(ogExpansionCodeName, ogCardNumber + 1))
                    }) {
                        Text("Print JSON and go to next card")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                SelectionContainer {
                    Text(newCard.toJsonString())
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
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
                // https://github.com/JetBrains/compose-multiplatform/blob/master/tutorials/Tab_Navigation/README.md#known-problems
                BasicTextField(
                    modifier = Modifier.padding(top = 4.dp),
                    value = newValue ?: "",
                    onValueChange = onValueChange,
                    singleLine = true, //Needed for Tab navigation; Tab navigation doesn't work with multiline.
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun SpecificEditableRow(
    key: String,
    ogValue: String?,
    newValue: String?,
    possibleValues: List<String>,
    onValueChange: (String?) -> Unit
) {
    Card(
        modifier = Modifier
            .height(30.dp)
            .widthIn(350.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(30.dp))
            .border(width = 1.dp, color = Color.Gray)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            //Key
            Text(key, modifier = Modifier.width(150.dp).fillMaxHeight().background(Color.LightGray).padding(start = 8.dp))
            //OgValue
            Text(ogValue.toString(), modifier = Modifier.width(150.dp).fillMaxHeight())
            //Value, we expect a value from a list of possible values
            Row {
                Checkbox(checked = newValue == null, onCheckedChange = { newValue ->
                    if (newValue) {
                        onValueChange(null)
                    } else {
                        onValueChange("")
                    }
                })
                var isExpanded by remember { mutableStateOf(false) }
                val textFieldState = rememberTextFieldState()
                // https://composables.com/material3/exposeddropdownmenubox
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = !isExpanded },
                ) {
                    TextField(
                        value = newValue ?: "null",
                        readOnly = true,
                        onValueChange = { onValueChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(newValue ?: "null") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false },
                    ) {
                        possibleValues.forEach { possibleValue ->
                            DropdownMenuItem(
                                onClick = {
                                    textFieldState.setTextAndPlaceCursorAtEnd(possibleValue)
                                    onValueChange(possibleValue)
                                    isExpanded = false
                                },
                            ) {
                                Text(possibleValue)
                            }
                        }
                    }
                }
            }
        }
    }
}
