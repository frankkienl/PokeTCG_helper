package nl.frankkie.poketcghelper.desktop_utils

import nl.frankkie.poketcghelper.initializeCards
import nl.frankkie.poketcghelper.model.PokeExpansion
import nl.frankkie.poketcghelper.model.PokePrint
import nl.frankkie.poketcghelper.model.PokeRarity
import nl.frankkie.poketcghelper.model.PokeType

lateinit var cardSetNames: List<String>

suspend fun main() {
    val cardSets = initializeCards()

    val listOfCardSetNames = cardSets.map { it.codeName }
    cardSetNames = listOfCardSetNames.toList()

    cardSets.forEach {
        doubleCheckData(it)
    }

//    val time = measureTime {
//        findRelatedCards(cardSets)
//    }
//    println("Related cards took $time")
}

fun doubleCheckData(pokeExpansion: PokeExpansion) {
    println("checking ${pokeExpansion.displayName}")
    println("=================================")
    //Make sure there are no typo's in the data
    val pokeCardSetIds = pokeExpansion.packs.map { somePack -> somePack.id }
    pokeExpansion.cards.forEach { someCard ->
        //Pack id
        if (someCard.packId != null) {
            if (!pokeCardSetIds.contains(someCard.packId)) {
                println("---------------------------------")
                println("Card contains non-existing Pack")
                println(someCard)
                println("=================================")
                //throw Exception("Card contains non-existing Pack")
            }
        }
        //CardSet
        if (someCard.expansion != null) {
            if (!cardSetNames.contains(someCard.expansion)) {
                println("---------------------------------")
                println("Card contains non-existing CardSet")
                println(someCard)
                println("=================================")
                //throw Exception("Card contains non-existing CardSet")
            }
        }
        //Rarity
        if (someCard.pokeRarity != null) {
            try {
                //this will //throw if pokeRarity-string does not match with an Enum value
                PokeRarity.valueOf(someCard.pokeRarity)
            } catch (e: Exception) {
                println("---------------------------------")
                println("Card contains non-existing Rarity")
                println(someCard)
                println("=================================")
                //throw Exception("Card contains non-existing Rarity")
            }
        }
        //Type
        if (someCard.pokeType != null) {
            try {
                //this will //throw if pokeRarity-string does not match with an Enum value
                PokeType.valueOf(someCard.pokeType)
            } catch (e: Exception) {
                println("---------------------------------")
                println("Card contains non-existing Type")
                println(someCard)
                println("=================================")
                //throw Exception("Card contains non-existing Type")
            }
        }
        //Weakness
        if (someCard.pokeWeakness != null) {
            try {
                //this will //throw if pokeRarity-string does not match with an Enum value
                PokeType.valueOf(someCard.pokeWeakness)
            } catch (e: Exception) {
                println("---------------------------------")
                println("Card contains non-existing Weakness")
                println(someCard)
                println("=================================")
                //throw Exception("Card contains non-existing Weakness")
            }
        }
        //Print
        if (someCard.pokePrint != null) {
            try {
                //this will //throw if pokeRarity-string does not match with an Enum value
                PokePrint.valueOf(someCard.pokePrint)
            } catch (e: Exception) {
                println("---------------------------------")
                println("Card contains non-existing Flair")
                println(someCard)
                println("=================================")
                //throw Exception("Card contains non-existing Flair")
            }
        }
        //PokeStage
        if (someCard.pokeStage != null) {
            //Basic=0, Stage 1=1, Stage 2=2;
            if (someCard.pokeStage < 0 || someCard.pokeStage > 2) {
                println("---------------------------------")
                println("Card contains non-existing Stage")
                println(someCard)
                println("=================================")
                //throw Exception("Card contains non-existing Stage")
            }
        }
        //PokeHp
        if (someCard.pokeHp != null) {
            //HP should be positive number
            if (someCard.pokeHp < 0) {
                println("---------------------------------")
                println("Card contains non-existing HP")
                println(someCard)
                println("=================================")
                //throw Exception("Card contains non-existing HP")
            }
        }
        //Retreat
        if (someCard.pokeRetreat != null) {
            //Retreat should be positive number, up to 5
            if (someCard.pokeRetreat < 0 || someCard.pokeRetreat > 5) {
                println("---------------------------------")
                println("Card contains non-existing Retreat")
                println(someCard)
                println("=================================")
                //throw Exception("Card contains non-existing Retreat")
            }
        }
        //Evolves from
        if (someCard.pokeEvolvesFrom != null) {
            val previousEvolutionName = someCard.pokeEvolvesFrom
            val previousEvolutionCard = pokeExpansion.cards.find { otherCard -> otherCard.pokeEvolvesFrom == previousEvolutionName }
            if (previousEvolutionCard == null) {
                println("---------------------------------")
                println("Card evolved from non-existing other card")
                println(someCard)
                println("=================================")
                //throw Exception("Card evolved from non-existing other card")
            }
        }
    }
}