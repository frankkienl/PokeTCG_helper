package nl.frankkie.poketcghelper

import nl.frankkie.poketcghelper.model.PokeCardSet
import org.jetbrains.compose.resources.ExperimentalResourceApi
import poketcg_helper.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
suspend fun initializeCards(): PokeCardSet {
    val jsonString = Res.readBytes("files/cardset_genetic_apex.json").decodeToString()
    return initializeCards(jsonString)
}