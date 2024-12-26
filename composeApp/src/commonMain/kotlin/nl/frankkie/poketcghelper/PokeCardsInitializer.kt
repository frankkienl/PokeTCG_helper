package nl.frankkie.poketcghelper

import kotlinx.serialization.json.Json
import nl.frankkie.poketcghelper.model.PokeCardSet
import org.jetbrains.compose.resources.ExperimentalResourceApi
import poketcg_helper.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
suspend fun initializeCards(): List<PokeCardSet> {
    //Genetic Apex
    val jsonStringGeneticApex = Res.readBytes("files/cardset_genetic_apex.json").decodeToString()
    val cardSetGeneticApex = initializeCardsFromJson(jsonStringGeneticApex)
    //Mythical Island
    val jsonStringMythicalIsland = Res.readBytes("files/cardset_mythical_island.json").decodeToString()
    val cardSetMythicalIsland = initializeCardsFromJson(jsonStringMythicalIsland)
    //Promo A
    val jsonStringPromo = Res.readBytes("files/cardset_promo_a.json").decodeToString()
    val cardSetPromo = initializeCardsFromJson(jsonStringPromo)
    //Done
    return listOf(cardSetGeneticApex, cardSetMythicalIsland, cardSetPromo)
}

fun initializeCardsFromJson(jsonString: String): PokeCardSet {
    val tempCardSet = Json.decodeFromString<PokeCardSet>(jsonString)
    return tempCardSet
}