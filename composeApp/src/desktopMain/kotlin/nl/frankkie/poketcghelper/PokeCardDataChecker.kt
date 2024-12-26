package nl.frankkie.poketcghelper

import nl.frankkie.poketcghelper.model.PokeCardSet
import nl.frankkie.poketcghelper.model.PokeFlair
import nl.frankkie.poketcghelper.model.PokeRarity
import nl.frankkie.poketcghelper.model.PokeType

suspend fun main() {
    val cardSets = initializeCards()
    cardSets.forEach {
        doubleCheckData(it)
    }

    findRelatedCards(cardSets)
}

fun doubleCheckData(pokeCardSet: PokeCardSet) {
    println("checking ${pokeCardSet.displayName}")
    println("=================================")
    //Make sure there are no typo's in the data
    val pokeCardSetIds = pokeCardSet.packs.map { somePack -> somePack.id }
    pokeCardSet.cards.forEach { someCard ->
        //Pack id
        if (someCard.packId != null) {
            if (!pokeCardSetIds.contains(someCard.packId)) {
                println("---------------------------------")
                println("Card contains non-existing Pack")
                println(someCard)
                println("=================================")
                throw Exception("Card contains non-existing Pack")
            }
        }
        //Rarity
        if (someCard.pokeRarity != null) {
            try {
                //this will throw if pokeRarity-string does not match with an Enum value
                PokeRarity.valueOf(someCard.pokeRarity)
            } catch (e: Exception) {
                println("---------------------------------")
                println("Card contains non-existing Rarity")
                println(someCard)
                println("=================================")
                throw Exception("Card contains non-existing Rarity")
            }
        }
        //Type
        if (someCard.pokeType != null) {
            try {
                //this will throw if pokeRarity-string does not match with an Enum value
                PokeType.valueOf(someCard.pokeType)
            } catch (e: Exception) {
                println("---------------------------------")
                println("Card contains non-existing Type")
                println(someCard)
                println("=================================")
                throw Exception("Card contains non-existing Type")
            }
        }
        //Flair
        if (someCard.pokeFlair != null) {
            try {
                //this will throw if pokeRarity-string does not match with an Enum value
                PokeFlair.valueOf(someCard.pokeFlair)
            } catch (e: Exception) {
                println("---------------------------------")
                println("Card contains non-existing Flair")
                println(someCard)
                println("=================================")
                throw Exception("Card contains non-existing Flair")
            }
        }
        //Evolves from
        if (someCard.pokeEvolvesFrom != null) {
            val previousEvolutionName = someCard.pokeEvolvesFrom
            val previousEvolutionCard = pokeCardSet.cards.find { otherCard -> otherCard.pokeEvolvesFrom == previousEvolutionName }
            if (previousEvolutionCard == null) {
                println("---------------------------------")
                println("Card evolved from non-existing other card")
                println(someCard)
                println("=================================")
                throw Exception("Card evolved from non-existing other card")
            }
        }
    }
}