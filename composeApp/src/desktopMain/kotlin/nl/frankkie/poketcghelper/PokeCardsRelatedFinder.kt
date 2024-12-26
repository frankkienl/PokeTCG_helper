package nl.frankkie.poketcghelper

import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeCardSet


fun findRelatedCards(cardSets: List<PokeCardSet>) {
    //Find cards with same name, like 'Mew', 'Mew ex'
    findSameNameCards(cardSets)
    //Find previous evolution, like 'Ivysaur', 'Bulbasaur'
    //Find next evolution, like 'Ivysaur', 'Venasaur'
}

private fun findSameNameCards(cardSets: List<PokeCardSet>) {
    cardSets.forEach { cardSet ->
        cardSet.cards.forEach { card ->
            findSameNameCards(cardSets, card.pokeName)
        }
    }
}

private fun findSameNameCards(cardSets: List<PokeCardSet>, nameToFind: String) {

    cardSets.forEach { cardSet ->
        val resultsFromSet = mutableListOf<PokeCard>()
        cardSet.cards.forEach { card ->
            if (card.pokeName.removeSuffix(" ex") == nameToFind.removeSuffix(" ex")) {
                resultsFromSet.add(card)
            }
        }

    }
}