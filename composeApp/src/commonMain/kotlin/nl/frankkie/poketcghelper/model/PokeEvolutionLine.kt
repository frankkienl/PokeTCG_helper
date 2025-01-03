package nl.frankkie.poketcghelper.model

data class PokeEvolutionLine(val names: List<String>, val cardsPerPokemon: List<Set<PokeCard>>) {
    override fun toString(): String {
        var sb = ""
        names.forEachIndexed { index, item ->
            sb += (item)
            if (index < cardsPerPokemon.size - 1) {
                sb += (" -> ")
            }
        }
        return sb
    }
}

val DUMMY_EVOLUTIONS: List<PokeEvolutionLine> = listOf(
    PokeEvolutionLine(
        listOf("Bulbasaur", "Ivysaur", "Venasaur"),
        listOf(
            setOf(PokeCard(1, "Bulbasaur", "")),
            setOf(PokeCard(2, "Ivysaur", "")),
            setOf(PokeCard(3, "Venasaur", "")),
        )
    )
)