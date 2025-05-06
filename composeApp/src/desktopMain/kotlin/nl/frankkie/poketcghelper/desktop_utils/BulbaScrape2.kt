package nl.frankkie.poketcghelper.desktop_utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.frankkie.poketcghelper.initializeCardsFromJson
import org.jetbrains.compose.resources.ExperimentalResourceApi
import poketcg_helper.composeapp.generated.resources.Res
import java.io.File


val DEBUG = true

fun main() {
    //CoroutineScope(Dispatchers.Main).launch { bulbaScrap2() }
    CoroutineScope(Dispatchers.Main).launch { fixImageUrls() }
}

val bulbaBaseUrl1 = "https://bulbapedia.bulbagarden.net/wiki/"
val bulbaBaseUrl2 = "https://bulbapedia.bulbagarden.net/w/index.php?title="
val bulbaImageUrl = "https://bulbapedia.bulbagarden.net/wiki/File:"

@OptIn(ExperimentalResourceApi::class)
suspend fun bulbaScrap2() {
    val ktorClient = HttpClient(CIO)

    val jsonStringCelestialGuardians = Res.readBytes("files/expansions/A3/expansion_celestial_guardians.json").decodeToString()
    val expansionCelestialGuardians = initializeCardsFromJson(jsonStringCelestialGuardians)

    val skip = 0

    expansionCelestialGuardians.cards.forEach { card ->

        if (card.number < skip) {
            return@forEach
        }

        if (!card.pokeName.contains(" ")) {
            return@forEach
        }

        // For each card, check the Bulbapedia page, and correct any data
        val bulbaUrl1 = "${bulbaBaseUrl1}${card.pokeName.replace(" ", "_")}_(Celestial_Guardians_${card.number})"
        val bulbaUrl2 = "${bulbaBaseUrl1}${card.pokeName.replace(" ", "_")}_(Celestial_Guardians_${card.number})&action=edit"
        println("Bulbapedia URL: $bulbaUrl1")

        val response = ktorClient.request(bulbaUrl2)
        val body: String = response.body()
        //Get the information from the body
        val enName = "\\|en name=(.+)\n".toRegex().find(body)?.groupValues?.get(1)
        val hp = "\\|hp=(\\d+)\n".toRegex().find(body)?.groupValues?.get(1)
        val weakness = "\\|weakness=(.+)\n".toRegex().find(body)?.groupValues?.get(1)
        val retreat = "\\|retreat cost=(\\d+)\n".toRegex().find(body)?.groupValues?.get(1)
        val evoStage = "\\|stage=(.+)\n".toRegex().find(body)?.groupValues?.get(1)

        // image
        val imageFileName = "${card.pokeName.replace(" ", "")}CelestialGuardians${card.number}.png"
        val responseImg = ktorClient.request("$bulbaImageUrl$imageFileName")
        val bodyImg: String = responseImg.body()
        val actualImageUrl = "(https://archives\\.bulbagarden\\.net/media/upload/.+/$imageFileName)".toRegex().find(bodyImg)?.groupValues?.get(1)

        val imageFile = File("/Users/frankbouwens/Desktop/$imageFileName")
        if (actualImageUrl == null) {
            println("Image not found")
            return@forEach
        }
        val responseImgByteArray: ByteArray = ktorClient.request(actualImageUrl).body()
        imageFile.writeBytes(responseImgByteArray)
        println("Image found: $actualImageUrl")

        card.copy(
            pokeName = enName ?: card.pokeName,
            pokeHp = hp?.toIntOrNull() ?: card.pokeHp,
            //pokeWeakness = weakness ?: card.pokeWeakness,
            //pokeRetreat = retreat?.toIntOrNull() ?: card.pokeRetreat,
            //pokeStage = evoStage?: card.pokeStage,
            imageUrl = imageFileName,
        )
    }

    ktorClient.close()
}

@OptIn(ExperimentalResourceApi::class)
suspend fun fixImageUrls() {
    val jsonStringCelestialGuardians = Res.readBytes("files/expansions/A3/expansion_celestial_guardians.json").decodeToString()
    val expansionCelestialGuardians = initializeCardsFromJson(jsonStringCelestialGuardians)
    expansionCelestialGuardians.cards.forEach { card ->
        val imageFileName = "${card.pokeName.replace(" ", "")}CelestialGuardians${card.number}.png"
        card.copy(
            imageUrl = imageFileName,
        )
        println(
            """
            {
                "number": ${card.number},
                "pokeName": "${card.pokeName}",
                "imageUrl": "$imageFileName",
                "pokeStage": ${card.pokeStage},
                "pokeEvolvesFrom": "${card.pokeEvolvesFrom}",
                "pokeHp": ${card.pokeHp},
                "pokeType": "${card.pokeType}",
                "pokeDesc": "${card.pokeDesc}",
                "pokeWeakness": "${card.pokeWeakness}",
                "pokeRetreat": ${card.pokeRetreat},
                "pokeIllustrator": "${card.pokeIllustrator}",
                "pokeRarity": "${card.pokeRarity}",
                "pokeFlavour": "${card.pokeFlavour}",
                "pokePrint": "${card.pokePrint}",
                "packId": "${card.packId}",
            },
        """.trimIndent()
        )
    }
}