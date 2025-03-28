package nl.frankkie.poketcghelper.model

import kotlinx.serialization.Serializable

@Serializable
data class PokeExpansion(
    val symbol: String,
    val codeName: String,
    val displayName: String,
    val numberOfCards: Int,
    val imageUrl: String,
    val packs: List<PokeExpansionPack>,
    val cards: List<PokeCard>
)

@Serializable
data class PokeExpansionPack(
    val id: String,
    val name: String,
    var imageUrl: String,
    var imageUrlSymbol: String? = null
)

@Serializable
data class PokeCard(
    val number: Int,
    val pokeName: String,
    val imageUrl: String,
    val pokeStage: Int? = 0,
    val pokeEvolvesFrom: String? = null,
    val pokeHp: Int? = null,
    val pokeType: String? = null,
    val pokeDesc: String? = null,
    val pokeWeakness: String? = null,
    val pokeRetreat: Int? = null,
    val pokeIllustrator: String? = null,
    val pokeRarity: String? = null,
    val pokeFlavour: String? = null,
    val pokePrint: String? = null,
    val packId: String? = null,
    var expansion: String? = null,
) {
    fun toJsonString(): String {
        var stringBuilder = ""
        stringBuilder += "{\n"
        stringBuilder += "\"number\": $number,\n"
        stringBuilder += "\"pokeName\": \"$pokeName\",\n"
        stringBuilder += "\"imageUrl\": \"$imageUrl\",\n"
        pokeStage?.let {
            stringBuilder += "\"pokeStage\": $pokeStage,\n"
        }
        pokeEvolvesFrom?.let {
            stringBuilder += "\"pokeEvolvesFrom\": \"$pokeEvolvesFrom\",\n"
        }
        pokeHp?.let {
            stringBuilder += "\"pokeHp\": $pokeHp,\n"
        }
        pokeType?.let {
            stringBuilder += "\"pokeType\": \"$pokeType\",\n"
        }
        pokeDesc?.let {
            stringBuilder += "\"pokeDesc\": \"${pokeDesc.replace("\"","\\\"")}\",\n"
        }
        pokeWeakness?.let {
            stringBuilder += "\"pokeWeakness\": \"$pokeWeakness\",\n"
        }
        pokeRetreat?.let {
            stringBuilder += "\"pokeRetreat\": $pokeRetreat,\n"
        }
        pokeIllustrator?.let {
            stringBuilder += "\"pokeIllustrator\": \"$it\",\n"
        }
        pokeRarity?.let {
            stringBuilder += "\"pokeRarity\": \"$pokeRarity\",\n"
        }
        pokeFlavour?.let {
            stringBuilder += "\"pokeFlavour\": \"${pokeFlavour.replace("\"","\\\"")}\",\n"
        }
        pokePrint?.let {
            stringBuilder += "\"pokePrint\": \"$pokePrint\",\n"
        }
        packId?.let {
            stringBuilder += "\"pokePackId\": \"$packId\",\n"
        }
        expansion?.let {
            stringBuilder += "\"pokeExpansion\": \"$it\",\n"
        }
        stringBuilder = stringBuilder.removeSuffix(",\n")
        stringBuilder += "\n},"
        return stringBuilder
    }
}

enum class PokeRarity(val codeName: String, val displayName: String, val imageUrl: String? = null, val symbolCount: Int? = null) {
    UNKNOWN(codeName = "UNKNOWN", displayName = "Unknown", imageUrl = null, symbolCount = 0), //0
    D1(codeName = "D1", displayName = "♦\uFE0F", imageUrl = "diamond.png", symbolCount = 1), //1
    D2(codeName = "D2", displayName = "♦\uFE0F ♦\uFE0F", imageUrl = "diamond.png", symbolCount = 2), //2
    D3(codeName = "D3", displayName = "♦\uFE0F ♦\uFE0F ♦\uFE0F", imageUrl = "diamond.png", symbolCount = 3), //3), //3
    D4(codeName = "D4", displayName = "♦\uFE0F ♦\uFE0F ♦\uFE0F ♦\uFE0F", imageUrl = "diamond.png", symbolCount = 4), //4
    S1(codeName = "S1", displayName = "★\uFE0F", imageUrl = "star.png", symbolCount = 1), //5), //5
    S2(codeName = "S2", displayName = "★\uFE0F ★\uFE0F", imageUrl = "star.png", symbolCount = 2), //6
    S3(codeName = "S3", displayName = "★\uFE0F ★\uFE0F ★\uFE0F", imageUrl = "star.png", symbolCount = 3), //7
    C(codeName = "C", displayName = "\uD83D\uDC51", imageUrl = "crown.png", symbolCount = 1), //8
    SHINY1(codeName = "SHINY1", displayName = "✨", imageUrl = "shiny.png", symbolCount = 1),
    SHINY2(codeName = "SHINY2", displayName = "✨ ✨", imageUrl = "shiny.png", symbolCount = 2),
    PROMO(codeName = "PROMO", displayName = "Promo", imageUrl = null, symbolCount = 0),
}

enum class PokePrint(val codeName: String, val displayName: String) {
    NONE(codeName = "NONE", "None"),
    HOLO(codeName = "HOLO", "Holo"),
    EX("EX", "EX"),
    FULL_ART(codeName = "FULL_ART", "Full Art"),
    FULL_ART_EX(codeName = "FULL_ART_EX", "Full Art EX"),
}

enum class PokeType(val codeName: String, val displayName: String, val imageUrl: String? = null) {
    UNKNOWN(codeName = "UNKNOWN", displayName = "Unknown", imageUrl = null),
    GRASS(codeName = "GRASS", displayName = "Grass", imageUrl = "grass.png"),
    FIRE(codeName = "FIRE", displayName = "Fire", imageUrl = "fire.png"),
    WATER(codeName = "WATER", displayName = "Water", imageUrl = "water.png"),
    LIGHTNING(codeName = "LIGHTNING", displayName = "Lightning", imageUrl = "lightning.png"),
    PSYCHIC(codeName = "PSYCHIC", displayName = "Psychic", imageUrl = "psychic.png"),
    FIGHTING(codeName = "FIGHTING", displayName = "Fighting", imageUrl = "fighting.png"),
    DARKNESS(codeName = "DARKNESS", displayName = "Darkness", imageUrl = "darkness.png"),
    METAL(codeName = "METAL", displayName = "Metal", imageUrl = "metal.png"),
    DRAGON(codeName = "DRAGON", displayName = "Dragon", imageUrl = "dragon.png"),
    COLORLESS(codeName = "COLORLESS", displayName = "Colorless", imageUrl = "colorless.png"),
    ITEM(codeName = "ITEM", displayName = "Item", imageUrl = null),
    SUPPORT(codeName = "SUPPORT", displayName = "Support", imageUrl = null),
}

enum class PokeStage(val number: Int, val codeName: String, val displayName: String) {
    BASIC(0, "BASIC", "Basic"),
    STAGE_1(1, "STAGE_1", "Stage 1"),
    STAGE_2(2, "STAGE_2", "Stage 2"),
}