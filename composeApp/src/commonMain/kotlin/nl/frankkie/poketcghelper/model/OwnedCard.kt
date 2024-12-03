package nl.frankkie.poketcghelper.model

data class OwnedCard(
    val pokeCardSet: PokeCardSet,
    val pokeCard: PokeCard,
    val amount: Int,
    val remarks: List<String>,
)
