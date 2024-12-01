package nl.frankkie.poketcghelper

import java.io.File

const val FILES_PATH = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/card_images/PROMO_A"

fun main() {
    val filesDir = File(FILES_PATH)
    if (!filesDir.exists()) {return}
    filesDir.listFiles()?.forEach { file ->
        if (file.isFile && file.name.endsWith("-143x200.png")) {
            file.renameTo(File(filesDir, file.name.replace("-143x200", "")))
        }
    }
}