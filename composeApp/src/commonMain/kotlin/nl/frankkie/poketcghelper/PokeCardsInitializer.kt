package nl.frankkie.poketcghelper

import kotlinx.serialization.json.Json
import nl.frankkie.poketcghelper.model.PokeExpansion
import org.jetbrains.compose.resources.ExperimentalResourceApi
import poketcg_helper.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
suspend fun initializeCards(): List<PokeExpansion> {
    //Genetic Apex
    val jsonStringGeneticApex = Res.readBytes("files/expansions/A1/expansion_genetic_apex.json").decodeToString()
    val cardSetGeneticApex = initializeCardsFromJson(jsonStringGeneticApex)
    //Mythical Island
    val jsonStringMythicalIsland = Res.readBytes("files/expansions/A1a/expansion_mythical_island.json").decodeToString()
    val cardSetMythicalIsland = initializeCardsFromJson(jsonStringMythicalIsland)
    //Promo A
    val jsonStringPromo = Res.readBytes("files/expansions/PROMO_A/expansion_promo_a.json").decodeToString()
    val cardSetPromo = initializeCardsFromJson(jsonStringPromo)
    //Space-Time
    val jsonStringSpaceTime = Res.readBytes("files/expansions/A2/expansion_space_time.json").decodeToString()
    val cardSetSpaceTime = initializeCardsFromJson(jsonStringSpaceTime)
    //Triumphant Light
    val jsonStringTriumphantLight = Res.readBytes("files/expansions/A2a/expansion_triumphant_light.json").decodeToString()
    val cardSetTriumphantLight = initializeCardsFromJson(jsonStringTriumphantLight)
    //Done
    return listOf(cardSetGeneticApex, cardSetMythicalIsland, cardSetPromo, cardSetSpaceTime, cardSetTriumphantLight)
}

fun initializeCardsFromJson(jsonString: String): PokeExpansion {
    val tempCardSet = Json.decodeFromString<PokeExpansion>(jsonString)
    return tempCardSet
}