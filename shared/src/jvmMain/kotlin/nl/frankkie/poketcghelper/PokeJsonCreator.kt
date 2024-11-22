package nl.frankkie.poketcghelper

import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeCardSetPack
import java.io.File

//const val IMAGES_PATH = "/Users/frankbouwens/priv/PokeTCG_helper/pokebeach/Genetic Apex (Pokemon TCG Pocket) - PokeBeach _ PokéBeach.com Forums_files/"
const val IMAGES_PATH = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/drawable/"

fun main() {
    val imagesDir = File(IMAGES_PATH)
    if (!imagesDir.exists()) {
        return
    }
    val listOfCards: List<PokeCard?>? = imagesDir.list()?.map {
        if (it.contains("-Genetic-Apex-143x200.png")) {
            val temp = it.removeSuffix("-Genetic-Apex-143x200.png")
            val number = temp.substring(0, it.indexOf("-")).toInt()
            val pokeName = temp
                .substring(it.indexOf("-") + 1)
                .replace("_", ".") //"Mr_-Mime" -> "Mr. Mime"
                .replace("-", " ") //"Gengar-ex" -> "Gengar ex"
            PokeCard(
                number,
                pokeName,
                it,
                packId = null
            )
        } else null
    }
    listOfCards?.let { safeListOfCards ->
        val sortedList = safeListOfCards.sortedBy { it?.number ?: -1 }
        sortedList.forEach {
            println(it?.toJsonString())
        }
    }
}

fun PokeCard.toJsonString(): String {
    val pokeCardString =
        """
        { 
            "number": ${number},
            "pokeName": "$pokeName",
            "imageUrl": "$imageUrl",
            "packId": ${
            if (packId != null) {
                "\"$packId\""
            } else {
                "null"
            }
        },
         },
        """.trimIndent()
    return pokeCardString
}