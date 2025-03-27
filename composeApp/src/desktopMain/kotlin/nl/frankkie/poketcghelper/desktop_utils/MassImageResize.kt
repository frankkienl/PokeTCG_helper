package nl.frankkie.poketcghelper.desktop_utils

import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


//const val IMAGES_RESIZE_PATH_SRC = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/card_images/MYTHICAL_ISLAND"
//const val IMAGES_RESIZE_PATH_DEST = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/card_images_small/MYTHICAL_ISLAND"

const val IMAGES_RESIZE_PATH_SRC_A2 = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2/card_images"
const val IMAGES_RESIZE_PATH_DEST_A2 = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2/card_images_small"

const val IMAGES_RESIZE_PATH_SRC_A2a = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2a/card_images"
const val IMAGES_RESIZE_PATH_DEST_A2a = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2a/card_images_small"

const val IMAGES_RESIZE_PATH_SRC_A2b = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2b/card_images"
const val IMAGES_RESIZE_PATH_DEST_A2b = "/Users/frankbouwens/priv/PokeTCG_helper/composeApp/src/commonMain/composeResources/files/expansions/A2b/card_images_small"

fun main() {
    main_A2b()
}

fun main_A2b() {
    val filesDir = File(IMAGES_RESIZE_PATH_SRC_A2b)
    if (!filesDir.exists()) {
        return
    }

    filesDir.listFiles()?.forEach { file ->
        if (file.isFile && file.name.endsWith(".jpg")) {
            processImage(file, File(IMAGES_RESIZE_PATH_DEST_A2b))
        }
    }
}

fun main_A2() {
    val filesDir = File(IMAGES_RESIZE_PATH_SRC_A2)
    if (!filesDir.exists()) {
        return
    }

    filesDir.listFiles()?.forEach { file ->
        if (file.isFile && file.name.endsWith(".jpg")) {
            processImage(file, File(IMAGES_RESIZE_PATH_DEST_A2))
        }
    }
}

fun main_A2a() {
    val filesDir = File(IMAGES_RESIZE_PATH_SRC_A2a)
    if (!filesDir.exists()) {
        return
    }

    filesDir.listFiles()?.forEach { file ->
        if (file.isFile && file.name.endsWith(".jpg")) {
            val tempNr = file.name.substring(0, file.name.indexOf("-")).toInt()
            if (tempNr >= 55) {
                processImage(file, File(IMAGES_RESIZE_PATH_DEST_A2a))
            }
        }
    }
}

fun processImage(
    fileSrc: File,
    fileDestDir: File,
    targetWidth: Int = 143,
    targetHeight: Int = 200
) {
    // https://www.baeldung.com/java-resize-image
    println("Processing image: ${fileSrc.name}")
    try {
        val originalImage = ImageIO.read(fileSrc)
        val resizedImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB)
        val graphics2D = resizedImage.createGraphics()
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null)
        graphics2D.dispose()
        val newFile = File(fileDestDir, fileSrc.name)
        ImageIO.write(resizedImage, "jpg", newFile)
        println("done")
    } catch (e: Exception) {
        println("processImage: file: $fileSrc ; error: ${e.message}")
        e.printStackTrace()
    }
}