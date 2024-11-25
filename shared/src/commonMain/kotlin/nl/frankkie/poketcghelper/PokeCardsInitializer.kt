package nl.frankkie.poketcghelper

import kotlinx.serialization.json.Json
import nl.frankkie.poketcghelper.model.PokeCardSet

fun initializeCards(jsonString: String): PokeCardSet {
    return Json.decodeFromString<PokeCardSet>(jsonString)
}