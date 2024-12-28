package nl.frankkie.poketcghelper

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.frankkie.poketcghelper.model.PokeCard
import nl.frankkie.poketcghelper.model.PokeCardSet
import nl.frankkie.poketcghelper.model.PokeType


fun findRelatedCards(cardSets: List<PokeCardSet>) {
    println("Looking for related cards")
    val allCards: List<PokeCard> = cardSets.flatMap { aCardSet -> aCardSet.cards }
    println("Found ${allCards.size} cards, from ${cardSets.size} cardSets")
    val nonPokemonTypes = listOf(PokeType.ITEM, PokeType.SUPPORT, PokeType.UNKNOWN)
    val onlyPokemon = allCards.filter { someCard -> (someCard.pokeType?.let { PokeType.valueOf(it) !in nonPokemonTypes } ?: false) }
    println("Found ${onlyPokemon.size} pokemon cards")
    //Find cards with same name, like 'Mew', 'Mew ex'
    val mapOfCardsWithSameName = findSameNameCards(onlyPokemon)
    //Find previous evolution, like 'Ivysaur', 'Bulbasaur'
    //Find next evolution, like 'Ivysaur', 'Venasaur'
    val mapOfCardsWithEvolutions = findEvolutionCards(onlyPokemon)
    //Merge all together now
    val mapOfCardsWithRelatedCards = mutableMapOf<PokeCard, Set<PokeCard>>()
    onlyPokemon.forEach {
        val mutableSetOfPokeCards = mutableSetOf<PokeCard>()
        val sameName = mapOfCardsWithSameName[it]
        val evolutions = mapOfCardsWithEvolutions[it]
        mutableSetOfPokeCards.addAll(sameName ?: emptySet())
        mutableSetOfPokeCards.addAll(evolutions ?: emptySet())
        mapOfCardsWithRelatedCards[it] = mutableSetOfPokeCards.toSet()
    }
    println("Done looking for related cards")
    //Writing results to json
    println(
        """
        ===================================
        
        
        ===================================
    """.trimIndent()
    )
    printJson(cardSets, mapOfCardsWithRelatedCards)
}

private fun findSameNameCards(cards: List<PokeCard>): Map<PokeCard, Set<PokeCard>> {
    val map: MutableMap<PokeCard, Set<PokeCard>> = mutableMapOf()
    cards.forEach { card ->
        map[card] = findSameNameCards(cards, card.pokeName)
    }
    return map.toMap()
}

private fun findSameNameCards(cards: List<PokeCard>, nameToFind: String): Set<PokeCard> {
    val cardsWithSameName = cards.filter { someCard ->
        someCard.pokeName.removeSuffix(" ex") == nameToFind.removeSuffix(" ex")
    }.toSet()
    println("$nameToFind: Found ${cardsWithSameName.size} cards with same name")
    return cardsWithSameName
}

private fun findEvolutionCards(cards: List<PokeCard>): Map<PokeCard, Set<PokeCard>> {
    val map: MutableMap<PokeCard, Set<PokeCard>> = mutableMapOf()
    cards.forEach { card ->
        val temp = findEvolutionCards(cards, card)
        if (temp.isNotEmpty()) {
            map[card] = temp.toSet()
        }
    }
    println("Found ${map.size} cards with evolutions")
    return map.toMap()
}

private fun findEvolutionCards(cards: List<PokeCard>, card: PokeCard): Set<PokeCard> {
    val setOfRelatedCards: MutableSet<PokeCard> = mutableSetOf()
    //find next evolution - Bulbasaur->Ivysaur (other card has this cards' name in 'evolvesFrom')
    val nextEvolutions = findNextEvolution(cards, card)
    //Find next next evolution - Bulbasaur->Ivysaur->Venasaur
    val nextNextEvolutions = nextEvolutions.flatMap { nextEvo -> findNextEvolution(cards, nextEvo) }
    //Add to set, because we only want unique cards
    setOfRelatedCards.addAll(nextEvolutions)
    setOfRelatedCards.addAll(nextNextEvolutions)

    //Find previous evolution - Charizard->Charmeleon
    val previousEvolutions = findPreviousEvolution(cards, card)
    //Find previous previous evolution - Charizard->Charmeleon->Charmander
    val previousPreviousEvolutions = previousEvolutions.flatMap { prevEvo -> findPreviousEvolution(cards, prevEvo) }
    setOfRelatedCards.addAll(previousEvolutions)
    setOfRelatedCards.addAll(previousPreviousEvolutions)

    println("${card.pokeName}: Found ${setOfRelatedCards.size} cards with evolutions")
    return setOfRelatedCards.toSet()
}

private fun findNextEvolution(cards: List<PokeCard>, card: PokeCard): Set<PokeCard> {
    return cards.filter { someCard -> someCard.pokeEvolvesFrom?.removeSuffix(" ex").equals(card.pokeName.removeSuffix(" ex")) }.toSet()
}

private fun findPreviousEvolution(cards: List<PokeCard>, card: PokeCard): Set<PokeCard> {
    if (card.pokeStage == 0) {
        return emptySet()
    }
    return cards.filter { someCard -> someCard.pokeName.removeSuffix(" ex") == card.pokeEvolvesFrom?.removeSuffix(" ex") }.toSet()
}

private fun printJson(cardSets: List<PokeCardSet>, map: Map<PokeCard, Set<PokeCard>>) {
    val relatedCardsPerSet = cardSets.map { cardSet ->
        val cardsInThisSet = map.entries.filter { entry -> cardSet.cards.contains(entry.key) }.sortedBy { entry -> entry.key.number }
        val temp = cardsInThisSet.map { entry ->
            PokeRelatedCards(entry.key.number, entry.value.map { someRelatedCard -> someRelatedCard.toPokeRelatedCard() })
        }
        PokeRelatedCardSet(cardSet.codeName, temp)
    }
    val prettyJson = Json { prettyPrint = true }
    val jsonString = prettyJson.encodeToString(relatedCardsPerSet)
    println(jsonString)
}

@Serializable
data class PokeRelatedCardSet(val cardSet: String, val cards: List<PokeRelatedCards>)
@Serializable
data class PokeRelatedCards(val number: Int, val relatedCards: List<PokeRelatedCard>)
@Serializable
data class PokeRelatedCard(val cardSet: String, val number: Int)
fun PokeCard.toPokeRelatedCard(): PokeRelatedCard = PokeRelatedCard(this.cardSet ?: "", this.number)
