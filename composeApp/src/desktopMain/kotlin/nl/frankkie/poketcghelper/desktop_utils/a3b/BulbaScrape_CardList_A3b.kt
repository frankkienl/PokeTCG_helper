package nl.frankkie.poketcghelper.desktop_utils.a3b

/*
 * Scrape data from Bulbapedia
 */

fun main() {
    processData_A3b()
}

fun processData_A3b() {
    val data = BULBA_DATA_A3b
    val lines = data.split("\n")
    val result = StringBuilder()
    result.append("[\n")
    for (line in lines) {
        if (line.startsWith("{{TCG Set List/header")) {
            continue
        }
        if (line.startsWith("|-")) {
            continue
        }

        val parts = line.split("||")
        if (parts.size > 1) {
            // card number
            val cardNumber = parts[0].substring(1).trim().substringBefore("/").trim()
            // poke name
            val pokeName = parts[1].split("|")[2].trim()
            // type
            val type = parts[2].trim()
            val typeText = type.substring(type.indexOf("|") + 1, type.indexOf("}")).uppercase()
            // rarity
            val rarity = parts[3].trim()
            val rarityText = when {
                rarity == "{{Rar/TCGP|Diamond|1}}" -> {
                    "D1"
                }

                rarity == "{{Rar/TCGP|Diamond|2}}" -> {
                    "D2"
                }

                rarity == "{{Rar/TCGP|Diamond|3}}" -> {
                    "D3"
                }

                rarity == "{{Rar/TCGP|Diamond|4}}" -> {
                    "D4"
                }

                rarity == "{{Rar/TCGP|Star|1}}" -> {
                    "S1"
                }

                rarity == "{{Rar/TCGP|Star|2}}" -> {
                    "S2"
                }

                rarity == "{{Rar/TCGP|Star|3}}" -> {
                    "S3"
                }

                rarity == "{{Rar/TCGP|Shiny|1}}" -> {
                    "SHINY1"
                }

                rarity == "{{Rar/TCGP|Shiny|2}}" -> {
                    "SHINY2"
                }

                rarity == "{{Rar/TCGP|Crown}}" -> {
                    "C"
                }

                else -> {
                    ""
                }
            }
            result.append(
                """
                {
                    "number": "${cardNumber.toInt()}",
                    "pokeName": "$pokeName",
                    "imageUrl": "",
                    "pokeType": "$typeText",
                    "pokeRarity": "$rarityText",
                },
            """.trimIndent()
            )
        }
    }
    result.append("]\n")
    println(result.toString())
}


//https://bulbapedia.bulbagarden.net/wiki/Celestial_Guardians_(TCG_Pocket)
const val BULBA_DATA_A3b = """
{{TCG Set List/header|tablecol=FD8|bordercol=C63|cellcol=FFC}}
|-
| 001/069 || {{TCG ID|Eevee Grove|Tropius|1}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}}
|-
| 002/069 || {{TCG ID|Eevee Grove|Leafeon|2}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}}
|-
| 003/069 || {{TCG ID|Eevee Grove|Bounsweet|3}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}}
|-
| 004/069 || {{TCG ID|Eevee Grove|Steenee|4}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}}
|-
| 005/069 || {{TCG ID|Eevee Grove|Tsareena|5}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}}
|-
| 006/069 || {{TCG ID|Eevee Grove|Applin|6}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}}
|-
| 007/069 || {{TCG ID|Eevee Grove|Appletun|7}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}}
|-
| 008/069 || {{TCG ID|Eevee Grove|Flareon|8}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|3}}
|-
| 009/069 || {{TCG ID|Eevee Grove|Flareon ex|9|Flareon}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|4}}
|-
| 010/069 || {{TCG ID|Eevee Grove|Torkoal|10}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}}
|-
| 011/069 || {{TCG ID|Eevee Grove|Litten|11}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}}
|-
| 012/069 || {{TCG ID|Eevee Grove|Torracat|12}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}}
|-
| 013/069 || {{TCG ID|Eevee Grove|Incineroar|13}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}}
|-
| 014/069 || {{TCG ID|Eevee Grove|Salandit|14}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}}
|-
| 015/069 || {{TCG ID|Eevee Grove|Salazzle|15}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}}
|-
| 016/069 || {{TCG ID|Eevee Grove|Vaporeon|16}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}}
|-
| 017/069 || {{TCG ID|Eevee Grove|Glaceon|17}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}}
|-
| 018/069 || {{TCG ID|Eevee Grove|Vanillite|18}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 019/069 || {{TCG ID|Eevee Grove|Vanillish|19}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 020/069 || {{TCG ID|Eevee Grove|Vanilluxe|20}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}}
|-
| 021/069 || {{TCG ID|Eevee Grove|Alomomola|21}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 022/069 || {{TCG ID|Eevee Grove|Popplio|22}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 023/069 || {{TCG ID|Eevee Grove|Brionne|23}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 024/069 || {{TCG ID|Eevee Grove|Primarina ex|24|Primarina}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|4}}
|-
| 025/069 || {{TCG ID|Eevee Grove|Jolteon|25}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}}
|-
| 026/069 || {{TCG ID|Eevee Grove|Joltik|26}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}}
|-
| 027/069 || {{TCG ID|Eevee Grove|Galvantula|27}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|2}}
|-
| 028/069 || {{TCG ID|Eevee Grove|Espeon|28}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}}
|-
| 029/069 || {{TCG ID|Eevee Grove|Woobat|29}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}}
|-
| 030/069 || {{TCG ID|Eevee Grove|Swoobat|30}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}}
|-
| 031/069 || {{TCG ID|Eevee Grove|Swirlix|31}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}}
|-
| 032/069 || {{TCG ID|Eevee Grove|Slurpuff|32}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}}
|-
| 033/069 || {{TCG ID|Eevee Grove|Sylveon|33}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}}
|-
| 034/069 || {{TCG ID|Eevee Grove|Sylveon ex|34|Sylveon}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|4}}
|-
| 035/069 || {{TCG ID|Eevee Grove|Mimikyu|35}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}}
|-
| 036/069 || {{TCG ID|Eevee Grove|Milcery|36}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}}
|-
| 037/069 || {{TCG ID|Eevee Grove|Alcremie|37}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}}
|-
| 038/069 || {{TCG ID|Eevee Grove|Barboach|38}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 039/069 || {{TCG ID|Eevee Grove|Whiscash|39}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}}
|-
| 040/069 || {{TCG ID|Eevee Grove|Mienfoo|40}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 041/069 || {{TCG ID|Eevee Grove|Mienshao|41}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}}
|-
| 042/069 || {{TCG ID|Eevee Grove|Carbink|42}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 043/069 || {{TCG ID|Eevee Grove|Umbreon|43}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|3}}
|-
| 044/069 || {{TCG ID|Eevee Grove|Sableye|44}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}}
|-
| 045/069 || {{TCG ID|Eevee Grove|Purrloin|45}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}}
|-
| 046/069 || {{TCG ID|Eevee Grove|Liepard|46}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}}
|-
| 047/069 || {{TCG ID|Eevee Grove|Mawile|47}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|1}}
|-
| 048/069 || {{TCG ID|Eevee Grove|Togedemaru|48}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|1}}
|-
| 049/069 || {{TCG ID|Eevee Grove|Meltan|49}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|1}}
|-
| 050/069 || {{TCG ID|Eevee Grove|Melmetal|50}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|2}}
|-
| 051/069 || {{TCG ID|Eevee Grove|Dratini|51}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Diamond|1}}
|-
| 052/069 || {{TCG ID|Eevee Grove|Dragonair|52}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Diamond|1}}
|-
| 053/069 || {{TCG ID|Eevee Grove|Dragonite ex|53|Dragonite}}{{TCGP Icon|ex}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Diamond|4}}
|-
| 054/069 || {{TCG ID|Eevee Grove|Drampa|54}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Diamond|2}}
|-
| 055/069 || {{TCG ID|Eevee Grove|Eevee|55}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 056/069 || {{TCG ID|Eevee Grove|Eevee ex|56|Eevee}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|4}}
|-
| 057/069 || {{TCG ID|Eevee Grove|Snorlax ex|57|Snorlax}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|4}}
|-
| 058/069 || {{TCG ID|Eevee Grove|Aipom|58}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 059/069 || {{TCG ID|Eevee Grove|Ambipom|59}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}}
|-
| 060/069 || {{TCG ID|Eevee Grove|Chatot|60}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 061/069 || {{TCG ID|Eevee Grove|Audino|61}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 062/069 || {{TCG ID|Eevee Grove|Minccino|62}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 063/069 || {{TCG ID|Eevee Grove|Cinccino|63}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}}
|-
| 064/069 || {{TCG ID|Eevee Grove|Skwovet|64}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 065/069 || {{TCG ID|Eevee Grove|Greedent|65}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}}
|-
| 066/069 || {{TCG ID|Eevee Grove|Eevee Bag|66}} || {{TCG Icon|Item}} || {{Rar/TCGP|Diamond|2}}
|-
| 067/069 || {{TCG ID|Eevee Grove|Leftovers|67}} || {{TCG Icon|Pokémon Tool}} || {{Rar/TCGP|Diamond|2}}
|-
| 068/069 || {{TCG ID|Eevee Grove|Hau|68}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}}
|-
| 069/069 || {{TCG ID|Eevee Grove|Penny|69}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}}
|-
| 070/069 || {{TCG ID|Eevee Grove|Leafeon|70}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|1}}
|-
| 071/069 || {{TCG ID|Eevee Grove|Flareon|71}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|1}}
|-
| 072/069 || {{TCG ID|Eevee Grove|Vaporeon|72}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|1}}
|-
| 073/069 || {{TCG ID|Eevee Grove|Glaceon|73}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|1}}
|-
| 074/069 || {{TCG ID|Eevee Grove|Jolteon|74}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|1}}
|-
| 075/069 || {{TCG ID|Eevee Grove|Espeon|75}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|1}}
|-
| 076/069 || {{TCG ID|Eevee Grove|Sylveon|76}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|1}}
|-
| 077/069 || {{TCG ID|Eevee Grove|Umbreon|77}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|1}}
|-
| 078/069 || {{TCG ID|Eevee Grove|Eevee|78}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}}
|-
| 079/069 || {{TCG ID|Eevee Grove|Flareon ex|79|Flareon}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|2}}
|-
| 080/069 || {{TCG ID|Eevee Grove|Primarina ex|80|Primarina}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|2}}
|-
| 081/069 || {{TCG ID|Eevee Grove|Sylveon ex|81|Sylveon}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|2}}
|-
| 082/069 || {{TCG ID|Eevee Grove|Dragonite ex|82|Dragonite}}{{TCGP Icon|ex}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Star|2}}
|-
| 083/069 || {{TCG ID|Eevee Grove|Eevee ex|83|Eevee}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|2}}
|-
| 084/069 || {{TCG ID|Eevee Grove|Snorlax ex|84|Snorlax}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|2}}
|-
| 085/069 || {{TCG ID|Eevee Grove|Hau|85}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}}
|-
| 086/069 || {{TCG ID|Eevee Grove|Penny|86}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}}
|-
| 087/069 || {{TCG ID|Eevee Grove|Flareon ex|87|Flareon}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|2}}
|-
| 088/069 || {{TCG ID|Eevee Grove|Primarina ex|88|Primarina}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|2}}
|-
| 089/069 || {{TCG ID|Eevee Grove|Sylveon ex|89|Sylveon}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|2}}
|-
| 090/069 || {{TCG ID|Eevee Grove|Dragonite ex|90|Dragonite}}{{TCGP Icon|ex}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Star|2}}
|-
| 091/069 || {{TCG ID|Eevee Grove|Snorlax ex|91|Snorlax}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|2}}
|-
| 092/069 || {{TCG ID|Eevee Grove|Eevee ex|92|Eevee}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|3}}
|-
| 093/069 || {{TCG ID|Eevee Grove|Pinsir|93}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|1}}
|-
| 094/069 || {{TCG ID|Eevee Grove|Lapras|94}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}}
|-
| 095/069 || {{TCG ID|Eevee Grove|Voltorb|95}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Shiny|1}}
|-
| 096/069 || {{TCG ID|Eevee Grove|Electrode|96}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Shiny|1}}
|-
| 097/069 || {{TCG ID|Eevee Grove|Ralts|97}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Shiny|1}}
|-
| 098/069 || {{TCG ID|Eevee Grove|Kirlia|98}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Shiny|1}}
|-
| 099/069 || {{TCG ID|Eevee Grove|Gardevoir|99}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Shiny|1}}
|-
| 100/069 || {{TCG ID|Eevee Grove|Ekans|100}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|1}}
|-
| 101/069 || {{TCG ID|Eevee Grove|Arbok|101}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|1}}
|-
| 102/069 || {{TCG ID|Eevee Grove|Farfetch'd|102}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|1}}
|-
| 103/069 || {{TCG ID|Eevee Grove|Moltres ex|103|Moltres}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Shiny|2}}
|-
| 104/069 || {{TCG ID|Eevee Grove|Articuno ex|104|Articuno}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|2}}
|-
| 105/069 || {{TCG ID|Eevee Grove|Zapdos ex|105|Zapdos}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Shiny|2}}
|-
| 106/069 || {{TCG ID|Eevee Grove|Gallade ex|106|Gallade}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|2}}
|-
| 107/069 || {{TCG ID|Eevee Grove|Eevee Bag|107}} || {{TCG Icon|Item}} || {{Rar/TCGP|Crown|1}}
|}
"""