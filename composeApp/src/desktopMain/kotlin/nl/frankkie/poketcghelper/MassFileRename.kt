package nl.frankkie.poketcghelper

import java.io.File

//const val FILES_PATH_RENAMES = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/card_images/PROMO_A"
const val FILES_PATH_RENAMES = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2a/card_images"
//const val FILES_PATH_RENAMES = "/Users/frankbouwens/priv/PokeTCG_helper/pokebeach/triumphant light"

fun main_old() {
    val filesDir = File(FILES_PATH_RENAMES)
    if (!filesDir.exists()) {
        return
    }
    filesDir.listFiles()?.forEach { file ->
        if (file.isFile && file.name.endsWith("-143x200.png")) {
            file.renameTo(File(filesDir, file.name.replace("-143x200", "")))
        }
    }
}

fun main() {
    val filesDir = File(FILES_PATH_RENAMES)
    if (!filesDir.exists()) {
        return
    }
    filesDir.listFiles()?.forEach { file ->
        if (file.isFile && file.name.endsWith(".webp")) {
            file.renameTo(File(filesDir, file.name.replace("Triumpant", "Triumphant").replace("_","-")))
        }
    }
}