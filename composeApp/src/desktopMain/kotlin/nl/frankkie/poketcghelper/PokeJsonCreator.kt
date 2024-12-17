package nl.frankkie.poketcghelper

import nl.frankkie.poketcghelper.model.PokeCard
import java.io.File

const val IMAGES_PATH = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/card_images/MYTHICAL_ISLAND/"
const val IMAGES_PATH2 = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/card_images2/"

fun main() {
    val imagesDir = File(IMAGES_PATH)
    if (!imagesDir.exists()) {
        return
    }
    val listOfCards: List<PokeCard?>? = imagesDir.list()?.map {
        if (it.contains("-Mythical-Island.jpg")) {
            val temp = it.removeSuffix("-Mythical-Island.jpg")
            val number = temp.substring(0, it.indexOf("-")).toInt()
            val pokeName = temp
                .substring(it.indexOf("-") + 1)
                .replace("_", ".") //"Mr_-Mime" -> "Mr. Mime"
                .replace("-", " ") //"Gengar-ex" -> "Gengar ex"
            PokeCard(
                number = number,
                pokeName = pokeName,
                pokeRarity = "UNKNOWN",
                pokeFlair = null,
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


fun main_genetic_apex() {
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
                number = number,
                pokeName = pokeName,
                pokeRarity = "UNKNOWN",
                pokeFlair = null,
                imageUrl = it,
                packId = null
            )
        } else null
    }
    listOfCards?.let { safeListOfCards ->
        val sortedList = safeListOfCards.sortedBy { it?.number ?: -1 }
        sortedList.forEach {
            //println(it?.toJsonString())
            println(it?.toCurl())
        }
    }
}

fun PokeCard.toCurl(): String {
    //https://www.pokebeach.com/news/2024/09/2-Ivysaur-Genetic-Apex.png
    val temp = """
        curl -O https://www.pokebeach.com/news/2024/09/${imageUrl.replace("-143x200","")}
    """.trimIndent()
    return temp
}

fun PokeCard.toJsonString(): String {
    val pokeCardString =
        """
        { 
            "number": ${number},
            "pokeName": "$pokeName",
            "pokeRarity": $pokeRarity,
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