package nl.frankkie.poketcghelper.model

import kotlinx.serialization.Serializable

@Serializable
data class PokeCardSet (
    val name: String,
    val numberOfCards: Int,
    val imageUrl: String,
    val packs: List<PokeCardSetPack>,
    val cards: List<PokeCard>
)

@Serializable
data class PokeCardSetPack (
    val id: String,
    val name: String,
    var imageUrl: String
)

@Serializable
data class PokeCard(
    val number: Int,
    val pokeName: String,
    val pokeRarity: String,
    val imageUrl: String,
    val packId: String?
)

enum class PokeRarity {
    UNKNOWN, //0
    DIAMOND_ONE, //1
    DIAMOND_TWO, //2
    DIAMOND_THREE, //3
    DIAMOND_FOUR, //4
    STAR_ONE, //5
    STAR_TWO, //6
    STAR_THREE, //7
    CROWN //8
}
