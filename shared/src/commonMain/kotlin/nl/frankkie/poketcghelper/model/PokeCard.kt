package nl.frankkie.poketcghelper.model

import kotlinx.serialization.Serializable

@Serializable
data class PokeCardSet(
    val name: String,
    val numberOfCards: Int,
    val imageUrl: String,
    val packs: List<PokeCardSetPack>,
    val cards: List<PokeCard>
)

@Serializable
data class PokeCardSetPack(
    val id: String,
    val name: String,
    var imageUrl: String,
    var imageUrlSymbol: String? = null
)

@Serializable
data class PokeCard(
    val number: Int,
    val pokeName: String,
    val pokeRarity: String,
    val pokeType: String? = null,
    val pokeFlair: String? = null,
    val imageUrl: String,
    val packId: String?
)

enum class PokeRarity(val codeName: String, val displayName: String, val imageUrl: String? = null, val symbolCount: Int? = null) {
    UNKNOWN(codeName = "UNKNOWN", displayName = "Unknown", imageUrl = null, symbolCount = 0), //0
    D1(codeName = "DIAMOND_1", displayName = "♦\uFE0F", imageUrl = "diamond.png", symbolCount = 1), //1
    D2(codeName = "DIAMOND_2", displayName = "♦\uFE0F ♦\uFE0F", imageUrl = "diamond.png", symbolCount = 2), //2
    D3(codeName = "DIAMOND_3", displayName = "♦\uFE0F ♦\uFE0F ♦\uFE0F", imageUrl = "diamond.png", symbolCount = 3), //3), //3
    D4(codeName = "DIAMOND_4", displayName = "♦\uFE0F ♦\uFE0F ♦\uFE0F ♦\uFE0F", imageUrl = "diamond.png", symbolCount = 4), //4
    S1(codeName = "STAR_1", displayName = "★\uFE0F", imageUrl = "star.png", symbolCount = 1), //5), //5
    S2(codeName = "STAR_2", displayName = "★\uFE0F ★\uFE0F", imageUrl = "star.png", symbolCount = 2), //6
    S3(codeName = "STAR_3", displayName = "★\uFE0F ★\uFE0F ★\uFE0F", imageUrl = "star.png", symbolCount = 3), //7
    C(codeName = "CROWN", displayName = "\uD83D\uDC51", imageUrl = "crown.png", symbolCount = 1) //8
}

enum class PokeFlair(val codeName: String, val displayName: String) {
    NONE(codeName = "NONE", "None"),
    FOIL(codeName = "FOIL", "Foil"),
    EX("EX", "EX"),
    FULL_ART(codeName = "FULL_ART", "Full Art"),
    FULL_ART_EX(codeName = "FULL_ART_EX", "Full Art EX"),
}

enum class PokeType(val codeName: String, val displayName: String, val imageUrl: String? = null) {
    NORMAL(codeName = "NORMAL", displayName = "Normal", imageUrl = "normal.png"),
    DARKNESS(codeName = "DARKNESS", displayName = "Darkness", imageUrl = "darkness.png"),
    DRAGON(codeName = "DRAGON", displayName = "Dragon", imageUrl = "dragon.png"),
    FIGHTING(codeName = "FIGHTING", displayName = "Fighting", imageUrl = "fighting.png"),
    FIRE(codeName = "FIRE", displayName = "Fire", imageUrl = "fire.png"),
    GRASS(codeName = "GRASS", displayName = "Grens", imageUrl = "grass.png"),
    LIGHTNING(codeName = "LIGHTNING", displayName = "Lightning", imageUrl = "lightning.png"),
    METAL(codeName = "METAL", displayName = "Metadata", imageUrl = "metal.png"),
    PSYCHIC(codeName = "PSYCHIC", displayName = "Psychic", imageUrl = "psychic.png"),
    WATER(codeName = "WATER", displayName = "Water", imageUrl = "water.png"),
    ITEM(codeName = "ITEM", displayName = "Item", imageUrl = null),
    SUPPORT(codeName = "SUPPORT", displayName = "Support", imageUrl = null),
}