package nl.frankkie.poketcghelper

import kotlinx.serialization.json.Json
import nl.frankkie.poketcghelper.model.PokeCardSet
import nl.frankkie.poketcghelper.model.PokeFlair
import nl.frankkie.poketcghelper.model.PokeRarity
import nl.frankkie.poketcghelper.model.PokeType

fun initializeCards(jsonString: String): PokeCardSet {
    val temp = Json.decodeFromString<PokeCardSet>(jsonString)
    doubleCheckData(temp)
    return temp
}

fun doubleCheckData(pokeCardSet: PokeCardSet) {
    //Make sure there are no typo's in the data
    val pokeCardSetIds = pokeCardSet.packs.map { somePack -> somePack.id }
    pokeCardSet.cards.forEach { someCard ->
        //Pack id
        if (someCard.packId!=null) {
            if (!pokeCardSetIds.contains(someCard.packId)) {
                print("---------------------------------")
                println("Card contains non-existing Pack")
                println(someCard)
                print("=================================")
                throw Exception("Card contains non-existing Pack")
            }
        }
        //Rarity
        if (someCard.pokeRarity!=null) {
            try {
                //this will throw if pokeRarity-string does not match with an Enum value
                PokeRarity.valueOf(someCard.pokeRarity)
            } catch (e: Exception) {
                print("---------------------------------")
                println("Card contains non-existing Rarity")
                println(someCard)
                print("=================================")
                throw Exception("Card contains non-existing Rarity")
            }
        }
        //Type
        if (someCard.pokeType!=null) {
            try {
                //this will throw if pokeRarity-string does not match with an Enum value
                PokeType.valueOf(someCard.pokeType)
            } catch (e: Exception) {
                print("---------------------------------")
                println("Card contains non-existing Type")
                println(someCard)
                print("=================================")
                throw Exception("Card contains non-existing Type")
            }
        }
        //Flair
        if (someCard.pokeFlair!=null) {
            try {
                //this will throw if pokeRarity-string does not match with an Enum value
                PokeFlair.valueOf(someCard.pokeFlair)
            } catch (e: Exception) {
                print("---------------------------------")
                println("Card contains non-existing Flair")
                println(someCard)
                print("=================================")
                throw Exception("Card contains non-existing Flair")
            }
        }
    }
}