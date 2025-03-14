package nl.frankkie.poketcghelper.desktop_utils

import nl.frankkie.poketcghelper.model.PokeCard
import java.io.File

//const val IMAGES_PATH_JSON = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/card_images/SPACE_TIME/"
const val IMAGES_PATH_JSON = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2a/card_images"
//const val IMAGES_PATH2 = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/card_images2/"

fun main() {
    val imagesDir = File(IMAGES_PATH_JSON)
    if (!imagesDir.exists()) {
        return
    }
    val listOfCards: List<PokeCard?>? = imagesDir.list()?.map {
        if (it.endsWith("-Triumphant-Light.jpg")) {
            val temp = it.removeSuffix("-Triumphant-Light.jpg")
            val number = temp.substring(0, it.indexOf("-")).toInt()
            val pokeName = temp
                .substring(it.indexOf("-") + 1)
                .replace("_", ".") //"Mr_-Mime" -> "Mr. Mime"
                .replace("-", " ") //"Gengar-ex" -> "Gengar ex"
            PokeCard(
                number = number,
                pokeName = pokeName,
                pokeRarity = "UNKNOWN",
                pokePrint = null,
                imageUrl = it,
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

