package nl.frankkie.poketcghelper.model

data class OwnedCard(
    val pokeExpansion: PokeExpansion,
    val pokeCard: PokeCard,
    val amount: Int,
    val remarks: List<String>,
)
