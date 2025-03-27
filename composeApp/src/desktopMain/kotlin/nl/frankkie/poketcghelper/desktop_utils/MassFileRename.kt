package nl.frankkie.poketcghelper.desktop_utils

import java.io.File

//const val FILES_PATH_RENAMES = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/card_images/PROMO_A"
const val FILES_PATH_RENAMES = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2a/card_images"
const val FILES_PATH_RENAMES2 = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2a/card_images_small"
//const val FILES_PATH_RENAMES = "/Users/frankbouwens/priv/PokeTCG_helper/pokebeach/triumphant light"

const val FILES_PATH_RENAMES_2b = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2b/card_images"

fun main() {
    processFixNumbers2b()
}

fun processFixNumbers2b() {
    val filesDir = File(FILES_PATH_RENAMES_2b)
    if (!filesDir.exists()) {
        return
    }
    filesDir.listFiles()?.forEach { file ->
        if (file.isFile && file.name.endsWith(".jpg")) {
            val tempNr = file.name.substring(0, file.name.indexOf("-")).toInt()
            if (tempNr >= 28 && tempNr < 89) {
                file.renameTo(File(filesDir, file.name.replace(tempNr.toString(), (tempNr - 2).toString())))
            }
        }
    }
}

fun processRemoveSizeSuffix() {
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

fun processFixTypo() {
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

fun processFixNumbers() {
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