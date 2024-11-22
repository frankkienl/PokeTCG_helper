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
    val imageUrl: String,
    val packId: String?
)






