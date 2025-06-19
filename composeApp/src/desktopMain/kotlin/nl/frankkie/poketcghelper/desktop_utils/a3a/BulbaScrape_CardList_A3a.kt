package nl.frankkie.poketcghelper.desktop_utils.a3a

/*
 * Scrape data from Bulbapedia
 */

fun main() {
    processData_A3a()
}

fun processData_A3a() {
    val data = BULBA_DATA_A3a
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
const val BULBA_DATA_A3a = """
{{TCG Set List/header|tablecol=E55|bordercol=92D|cellcol=FCC}}
|-
| 001/069 || {{TCG ID|Extradimensional Crisis|Petilil|1}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}}
|-
| 002/069 || {{TCG ID|Extradimensional Crisis|Lilligant|2}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}}
|-
| 003/069 || {{TCG ID|Extradimensional Crisis|Rowlet|3}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}}
|-
| 004/069 || {{TCG ID|Extradimensional Crisis|Dartrix|4}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}}
|-
| 005/069 || {{TCG ID|Extradimensional Crisis|Decidueye|5}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}}
|-
| 006/069 || {{TCG ID|Extradimensional Crisis|Buzzwole ex|6|Buzzwole}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|4}}
|-
| 007/069 || {{TCG ID|Extradimensional Crisis|Pheromosa|7}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}}
|-
| 008/069 || {{TCG ID|Extradimensional Crisis|Kartana|8}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}}
|-
| 009/069 || {{TCG ID|Extradimensional Crisis|Blacephalon|9}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}}
|-
| 010/069 || {{TCG ID|Extradimensional Crisis|Mantine|10}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 011/069 || {{TCG ID|Extradimensional Crisis|Carvanha|11}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 012/069 || {{TCG ID|Extradimensional Crisis|Sharpedo|12}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}}
|-
| 013/069 || {{TCG ID|Extradimensional Crisis|Shinx|13}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}}
|-
| 014/069 || {{TCG ID|Extradimensional Crisis|Luxio|14}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}}
|-
| 015/069 || {{TCG ID|Extradimensional Crisis|Luxray|15}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}}
|-
| 016/069 || {{TCG ID|Extradimensional Crisis|Blitzle|16}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}}
|-
| 017/069 || {{TCG ID|Extradimensional Crisis|Zebstrika|17}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}}
|-
| 018/069 || {{TCG ID|Extradimensional Crisis|Emolga|18}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}}
|-
| 019/069 || {{TCG ID|Extradimensional Crisis|Tapu Koko ex|19|Tapu Koko}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|4}}
|-
| 020/069 || {{TCG ID|Extradimensional Crisis|Xurkitree|20}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|2}}
|-
| 021/069 || {{TCG ID|Extradimensional Crisis|Zeraora|21}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}}
|-
| 022/069 || {{TCG ID|Extradimensional Crisis|Clefairy|22}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}}
|-
| 023/069 || {{TCG ID|Extradimensional Crisis|Clefable|23}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}}
|-
| 024/069 || {{TCG ID|Extradimensional Crisis|Phantump|24}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}}
|-
| 025/069 || {{TCG ID|Extradimensional Crisis|Trevenant|25}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}}
|-
| 026/069 || {{TCG ID|Extradimensional Crisis|Morelull|26}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}}
|-
| 027/069 || {{TCG ID|Extradimensional Crisis|Shiinotic|27}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}}
|-
| 028/069 || {{TCG ID|Extradimensional Crisis|Meditite|28}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 029/069 || {{TCG ID|Extradimensional Crisis|Medicham|29}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}}
|-
| 030/069 || {{TCG ID|Extradimensional Crisis|Baltoy|30}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 031/069 || {{TCG ID|Extradimensional Crisis|Claydol|31}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}}
|-
| 032/069 || {{TCG ID|Extradimensional Crisis|Rockruff|32}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 033/069 || {{TCG ID|Extradimensional Crisis|Lycanroc ex|33|Lycanroc}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|4}}
|-
| 034/069 || {{TCG ID|Extradimensional Crisis|Passimian|34}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 035/069 || {{TCG ID|Extradimensional Crisis|Sandygast|35}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 036/069 || {{TCG ID|Extradimensional Crisis|Palossand|36}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}}
|-
| 037/069 || {{TCG ID|Extradimensional Crisis|Alolan Meowth|37}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}}
|-
| 038/069 || {{TCG ID|Extradimensional Crisis|Alolan Persian|38}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}}
|-
| 039/069 || {{TCG ID|Extradimensional Crisis|Sandile|39}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}}
|-
| 040/069 || {{TCG ID|Extradimensional Crisis|Krokorok|40}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}}
|-
| 041/069 || {{TCG ID|Extradimensional Crisis|Krookodile|41}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}}
|-
| 042/069 || {{TCG ID|Extradimensional Crisis|Nihilego|42}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|3}}
|-
| 043/069 || {{TCG ID|Extradimensional Crisis|Guzzlord ex|43|Guzzlord}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|4}}
|-
| 044/069 || {{TCG ID|Extradimensional Crisis|Poipole|44}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}}
|-
| 045/069 || {{TCG ID|Extradimensional Crisis|Naganadel|45}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|3}}
|-
| 046/069 || {{TCG ID|Extradimensional Crisis|Alolan Diglett|46}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|1}}
|-
| 047/069 || {{TCG ID|Extradimensional Crisis|Alolan Dugtrio ex|47|Alolan Dugtrio}}{{TCGP Icon|ex}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|4}}
|-
| 048/069 || {{TCG ID|Extradimensional Crisis|Aron|48}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|1}}
|-
| 049/069 || {{TCG ID|Extradimensional Crisis|Lairon|49}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|1}}
|-
| 050/069 || {{TCG ID|Extradimensional Crisis|Aggron|50}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|2}}
|-
| 051/069 || {{TCG ID|Extradimensional Crisis|Ferroseed|51}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|1}}
|-
| 052/069 || {{TCG ID|Extradimensional Crisis|Ferrothorn|52}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|2}}
|-
| 053/069 || {{TCG ID|Extradimensional Crisis|Stakataka|53}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|2}}
|-
| 054/069 || {{TCG ID|Extradimensional Crisis|Lillipup|54}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 055/069 || {{TCG ID|Extradimensional Crisis|Herdier|55}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 056/069 || {{TCG ID|Extradimensional Crisis|Stoutland|56}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}}
|-
| 057/069 || {{TCG ID|Extradimensional Crisis|Stufful|57}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 058/069 || {{TCG ID|Extradimensional Crisis|Bewear|58}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}}
|-
| 059/069 || {{TCG ID|Extradimensional Crisis|Oranguru|59}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 060/069 || {{TCG ID|Extradimensional Crisis|Type: Null|60}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}}
|-
| 061/069 || {{TCG ID|Extradimensional Crisis|Silvally|61}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|3}}
|-
| 062/069 || {{TCG ID|Extradimensional Crisis|Celesteela|62}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|3}}
|-
| 063/069 || {{TCG ID|Extradimensional Crisis|Beast Wall|63}} || {{TCG Icon|Item}} || {{Rar/TCGP|Diamond|2}}
|-
| 064/069 || {{TCG ID|Extradimensional Crisis|Repel|64}} || {{TCG Icon|Item}} || {{Rar/TCGP|Diamond|2}}
|-
| 065/069 || {{TCG ID|Extradimensional Crisis|Electrical Cord|65}} || {{TCG Icon|Pokémon Tool}} || {{Rar/TCGP|Diamond|2}}
|-
| 066/069 || {{TCG ID|Extradimensional Crisis|Beastite|66}} || {{TCG Icon|Pokémon Tool}} || {{Rar/TCGP|Diamond|2}}
|-
| 067/069 || {{TCG ID|Extradimensional Crisis|Gladion|67}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}}
|-
| 068/069 || {{TCG ID|Extradimensional Crisis|Looker|68}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}}
|-
| 069/069 || {{TCG ID|Extradimensional Crisis|Lusamine|69}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}}
|-
| 070/069 || {{TCG ID|Extradimensional Crisis|Rowlet|70}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|1}}
|-
| 071/069 || {{TCG ID|Extradimensional Crisis|Pheromosa|71}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|1}}
|-
| 072/069 || {{TCG ID|Extradimensional Crisis|Blacephalon|72}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|1}}
|-
| 073/069 || {{TCG ID|Extradimensional Crisis|Alolan Meowth|73}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|1}}
|-
| 074/069 || {{TCG ID|Extradimensional Crisis|Silvally|74}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}}
|-
| 075/069 || {{TCG ID|Extradimensional Crisis|Celesteela|75}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}}
|-
| 076/069 || {{TCG ID|Extradimensional Crisis|Buzzwole ex|76|Buzzwole}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|2}}
|-
| 077/069 || {{TCG ID|Extradimensional Crisis|Tapu Koko ex|77|Tapu Koko}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|2}}
|-
| 078/069 || {{TCG ID|Extradimensional Crisis|Lycanroc ex|78|Lycanroc}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|2}}
|-
| 079/069 || {{TCG ID|Extradimensional Crisis|Guzzlord ex|79|Guzzlord}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|2}}
|-
| 080/069 || {{TCG ID|Extradimensional Crisis|Alolan Dugtrio ex|80|Alolan Dugtrio}}{{TCGP Icon|ex}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Star|2}}
|-
| 081/069 || {{TCG ID|Extradimensional Crisis|Gladion|81}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}}
|-
| 082/069 || {{TCG ID|Extradimensional Crisis|Looker|82}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}}
|-
| 083/069 || {{TCG ID|Extradimensional Crisis|Lusamine|83}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}}
|-
| 084/069 || {{TCG ID|Extradimensional Crisis|Tapu Koko ex|84|Tapu Koko}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|2}}
|-
| 085/069 || {{TCG ID|Extradimensional Crisis|Lycanroc ex|85|Lycanroc}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|2}}
|-
| 086/069 || {{TCG ID|Extradimensional Crisis|Guzzlord ex|86|Guzzlord}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|2}}
|-
| 087/069 || {{TCG ID|Extradimensional Crisis|Alolan Dugtrio ex|87|Alolan Dugtrio}}{{TCGP Icon|ex}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Star|2}}
|-
| 088/069 || {{TCG ID|Extradimensional Crisis|Buzzwole ex|88|Buzzwole}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|3}}
|-
| 089/069 || {{TCG ID|Extradimensional Crisis|Growlithe|89}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Shiny|1}}
|-
| 090/069 || {{TCG ID|Extradimensional Crisis|Arcanine|90}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Shiny|1}}
|-
| 091/069 || {{TCG ID|Extradimensional Crisis|Froakie|91}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}}
|-
| 092/069 || {{TCG ID|Extradimensional Crisis|Frogadier|92}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}}
|-
| 093/069 || {{TCG ID|Extradimensional Crisis|Greninja|93}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}}
|-
| 094/069 || {{TCG ID|Extradimensional Crisis|Jynx|94}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Shiny|1}}
|-
| 095/069 || {{TCG ID|Extradimensional Crisis|Pidgey|95}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|1}}
|-
| 096/069 || {{TCG ID|Extradimensional Crisis|Pidgeotto|96}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|1}}
|-
| 097/069 || {{TCG ID|Extradimensional Crisis|Pidgeot|97}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|1}}
|-
| 098/069 || {{TCG ID|Extradimensional Crisis|Aerodactyl|98}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|1}}
|-
| 099/069 || {{TCG ID|Extradimensional Crisis|Celebi ex|99|Celebi}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|2}}
|-
| 100/069 || {{TCG ID|Extradimensional Crisis|Arcanine ex|100|Arcanine}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Shiny|2}}
|-
| 101/069 || {{TCG ID|Extradimensional Crisis|Aerodactyl ex|101|Aerodactyl}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|2}}
|-
| 102/069 || {{TCG ID|Extradimensional Crisis|Pidgeot ex|102|Pidgeot}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|2}}
|-
| 103/069 || {{TCG ID|Extradimensional Crisis|Nihilego|103}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Crown|1}}
|}
"""