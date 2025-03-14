package nl.frankkie.poketcghelper.desktop_utils

import java.io.File

//const val FILES_PATH_RENAMES = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/card_images/PROMO_A"
const val FILES_PATH_RENAMES = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2a/card_images"
const val FILES_PATH_RENAMES2 = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2a/card_images_small"
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

fun main_old_2() {
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

fun main() {
    val filesDir = File(FILES_PATH_RENAMES2)
    if (!filesDir.exists()) {
        return
    }
    filesDir.listFiles()?.forEach { file ->
        if (file.isFile && file.name.endsWith(".jpg")) {
            val tempNr = file.name.substring(0, file.name.indexOf("-")).toInt()
            if (tempNr >= 55) {
                file.renameTo(File(filesDir, file.name.replace(tempNr.toString(), (tempNr + 1).toString())))
            }
        }
    }
}