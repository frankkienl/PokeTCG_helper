package nl.frankkie.poketcghelper

import java.io.File

// /opt/homebrew/bin/magick

val MAGICK_PATH = "/opt/homebrew/bin/magick"

val IMAGES_CONVERT_PATH_SRC_A2 = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2/card_images"
val IMAGES_CONVERT_PATH_DEST_A2 = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2/card_images"

val IMAGES_CONVERT_PATH_SRC_A2a = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2a/card_images"
val IMAGES_CONVERT_PATH_DEST_A2a = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2a/card_images"


fun main() {
    val filesDir = File(IMAGES_CONVERT_PATH_SRC_A2)
    if (!filesDir.exists()) {return}

    filesDir.listFiles()?.forEach { file ->
        if (file.isFile && file.name.endsWith(".webp")) {
            processImage(MAGICK_PATH, file)
        }
    }
}

fun processImage(magickPath: String, file: File) {
    println("Processing image: ${file.name}")
    try {
        val newFile = File(IMAGES_CONVERT_PATH_DEST_A2, file.name.replace(".webp", ".jpg"))
        val process = ProcessBuilder(MAGICK_PATH, file.absolutePath, newFile.absolutePath).start()
        process.waitFor()
        println("done")
    } catch (e: Exception) {
        println("processImage: file: $file ; error: ${e.message}")
        e.printStackTrace()
    }
}