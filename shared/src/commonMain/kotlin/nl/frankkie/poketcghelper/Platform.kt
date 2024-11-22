package nl.frankkie.poketcghelper

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform