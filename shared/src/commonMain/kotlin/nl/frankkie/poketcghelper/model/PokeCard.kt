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
    var imageUrl: String
)

@Serializable
data class PokeCard(
    val number: Int,
    val pokeName: String,
    val pokeRarity: String,
    val pokeFlair: String? = null,
    val imageUrl: String,
    val packId: String?
)

enum class PokeRarity(val codeName: String, val displayName: String) {
    UNKNOWN(codeName = "UNKNOWN", displayName = "Unknown"), //0
    D1(codeName = "DIAMOND_1", displayName = "♦\uFE0F"), //1
    D2(codeName = "DIAMOND_2", displayName = "♦\uFE0F ♦\uFE0F"), //2
    D3(codeName = "DIAMOND_3", displayName = "♦\uFE0F ♦\uFE0F ♦\uFE0F"), //3), //3
    D4(codeName = "DIAMOND_4", displayName = "♦\uFE0F ♦\uFE0F ♦\uFE0F ♦\uFE0F"), //4
    S1(codeName = "STAR_1", displayName = "★\uFE0F"), //5), //5
    S2(codeName = "STAR_2", displayName = "★\uFE0F ★\uFE0F"), //6
    S3(codeName = "STAR_3", displayName = "★\uFE0F ★\uFE0F ★\uFE0F"), //7
    C(codeName = "CROWN", displayName = "\uD83D\uDC51") //8
}

enum class PokeFlair(val codeName: String, val displayName: String) {
    NONE(codeName = "NONE", "None"),
    FOIL(codeName = "FOIL", "Foil"),
    EX("EX", "EX"),
    FULL_ART(codeName = "FULL_ART", "Full Art"),
    FULL_ART_EX(codeName = "FULL_ART_EX", "Full Art EX"),
}