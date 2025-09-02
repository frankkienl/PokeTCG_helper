package nl.frankkie.poketcghelper.desktop_utils.a4a

/*
 * Scrape data from Bulbapedia
 */

fun main() {
    processData_A4a()
}

fun processData_A4a() {
    val data = BULBA_DATA_A4a
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
const val BULBA_DATA_A4a = """
    {{TCG Set List/header|tablecol=3BF|bordercol=76E|cellcol=9EF}}
|-
| 001/071 || {{TCG ID|Secluded Springs|Hoppip|1}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}}
|-
| 002/071 || {{TCG ID|Secluded Springs|Skiploom|2}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}}
|-
| 003/071 || {{TCG ID|Secluded Springs|Jumpluff ex|3|Jumpluff}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|4}}
|-
| 004/071 || {{TCG ID|Secluded Springs|Sunkern|4}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}}
|-
| 005/071 || {{TCG ID|Secluded Springs|Sunflora|5}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}}
|-
| 006/071 || {{TCG ID|Secluded Springs|Celebi|6}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}}
|-
| 007/071 || {{TCG ID|Secluded Springs|Durant|7}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}}
|-
| 008/071 || {{TCG ID|Secluded Springs|Slugma|8}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}}
|-
| 009/071 || {{TCG ID|Secluded Springs|Magcargo|9}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}}
|-
| 010/071 || {{TCG ID|Secluded Springs|Entei ex|10|Entei}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|4}}
|-
| 011/071 || {{TCG ID|Secluded Springs|Fletchinder|11}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}}
|-
| 012/071 || {{TCG ID|Secluded Springs|Talonflame|12}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}}
|-
| 013/071 || {{TCG ID|Secluded Springs|Poliwag|13}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 014/071 || {{TCG ID|Secluded Springs|Poliwhirl|14}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}}
|-
| 015/071 || {{TCG ID|Secluded Springs|Tentacool|15}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 016/071 || {{TCG ID|Secluded Springs|Tentacruel|16}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}}
|-
| 017/071 || {{TCG ID|Secluded Springs|Slowpoke|17}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 018/071 || {{TCG ID|Secluded Springs|Slowking|18}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}}
|-
| 019/071 || {{TCG ID|Secluded Springs|Jynx|19}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 020/071 || {{TCG ID|Secluded Springs|Suicune ex|20|Suicune}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|4}}
|-
| 021/071 || {{TCG ID|Secluded Springs|Feebas|21}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 022/071 || {{TCG ID|Secluded Springs|Milotic|22}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}}
|-
| 023/071 || {{TCG ID|Secluded Springs|Mantyke|23}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}}
|-
| 024/071 || {{TCG ID|Secluded Springs|Cryogonal|24}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}}
|-
| 025/071 || {{TCG ID|Secluded Springs|Raikou ex|25|Raikou}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|4}}
|-
| 026/071 || {{TCG ID|Secluded Springs|Tynamo|26}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}}
|-
| 027/071 || {{TCG ID|Secluded Springs|Eelektrik|27}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}}
|-
| 028/071 || {{TCG ID|Secluded Springs|Eelektross|28}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|2}}
|-
| 029/071 || {{TCG ID|Secluded Springs|Stunfisk|29}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}}
|-
| 030/071 || {{TCG ID|Secluded Springs|Yamper|30}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}}
|-
| 031/071 || {{TCG ID|Secluded Springs|Boltund|31}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}}
|-
| 032/071 || {{TCG ID|Secluded Springs|Misdreavus|32}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}}
|-
| 033/071 || {{TCG ID|Secluded Springs|Mismagius|33}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}}
|-
| 034/071 || {{TCG ID|Secluded Springs|Galarian Corsola|34}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}}
|-
| 035/071 || {{TCG ID|Secluded Springs|Galarian Cursola|35}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}}
|-
| 036/071 || {{TCG ID|Secluded Springs|Latias|36}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}}
|-
| 037/071 || {{TCG ID|Secluded Springs|Latios|37}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}}
|-
| 038/071 || {{TCG ID|Secluded Springs|Frillish|38}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}}
|-
| 039/071 || {{TCG ID|Secluded Springs|Jellicent|39}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}}
|-
| 040/071 || {{TCG ID|Secluded Springs|Diglett|40}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 041/071 || {{TCG ID|Secluded Springs|Dugtrio|41}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}}
|-
| 042/071 || {{TCG ID|Secluded Springs|Poliwrath ex|42|Poliwrath}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|4}}
|-
| 043/071 || {{TCG ID|Secluded Springs|Phanpy|43}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 044/071 || {{TCG ID|Secluded Springs|Donphan|44}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}}
|-
| 045/071 || {{TCG ID|Secluded Springs|Relicanth|45}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 046/071 || {{TCG ID|Secluded Springs|Dwebble|46}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}}
|-
| 047/071 || {{TCG ID|Secluded Springs|Crustle|47}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}}
|-
| 048/071 || {{TCG ID|Secluded Springs|Seviper|48}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}}
|-
| 049/071 || {{TCG ID|Secluded Springs|Zorua|49}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}}
|-
| 050/071 || {{TCG ID|Secluded Springs|Zoroark|50}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|3}}
|-
| 051/071 || {{TCG ID|Secluded Springs|Inkay|51}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}}
|-
| 052/071 || {{TCG ID|Secluded Springs|Malamar|52}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}}
|-
| 053/071 || {{TCG ID|Secluded Springs|Skrelp|53}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}}
|-
| 054/071 || {{TCG ID|Secluded Springs|Dragalge|54}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}}
|-
| 055/071 || {{TCG ID|Secluded Springs|Altaria|55}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Diamond|3}}
|-
| 056/071 || {{TCG ID|Secluded Springs|Farfetch'd|56}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 057/071 || {{TCG ID|Secluded Springs|Lickitung|57}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 058/071 || {{TCG ID|Secluded Springs|Lickilicky|58}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}}
|-
| 059/071 || {{TCG ID|Secluded Springs|Igglybuff|59}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|3}}
|-
| 060/071 || {{TCG ID|Secluded Springs|Teddiursa|60}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 061/071 || {{TCG ID|Secluded Springs|Ursaring|61}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}}
|-
| 062/071 || {{TCG ID|Secluded Springs|Miltank|62}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}}
|-
| 063/071 || {{TCG ID|Secluded Springs|Azurill|63}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|3}}
|-
| 064/071 || {{TCG ID|Secluded Springs|Swablu|64}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 065/071 || {{TCG ID|Secluded Springs|Zangoose|65}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}}
|-
| 066/071 || {{TCG ID|Secluded Springs|Fletchling|66}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}}
|-
| 067/071 || {{TCG ID|Secluded Springs|Inflatable Boat|67}} || {{TCG Icon|Pokémon Tool}} || {{Rar/TCGP|Diamond|2}}
|-
| 068/071 || {{TCG ID|Secluded Springs|Memory Light|68}} || {{TCG Icon|Pokémon Tool}} || {{Rar/TCGP|Diamond|2}}
|-
| 069/071 || {{TCG ID|Secluded Springs|Whitney|69}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}}
|-
| 070/071 || {{TCG ID|Secluded Springs|Traveling Merchant|70}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}}
|-
| 071/071 || {{TCG ID|Secluded Springs|Morty|71}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}}
|-
| 072/071 || {{TCG ID|Secluded Springs|Milotic|72}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|1}}
|-
| 073/071 || {{TCG ID|Secluded Springs|Stunfisk|73}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|1}}
|-
| 074/071 || {{TCG ID|Secluded Springs|Yamper|74}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|1}}
|-
| 075/071 || {{TCG ID|Secluded Springs|Latios|75}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|1}}
|-
| 076/071 || {{TCG ID|Secluded Springs|Phanpy|76}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|1}}
|-
| 077/071 || {{TCG ID|Secluded Springs|Azurill|77}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}}
|-
| 078/071 || {{TCG ID|Secluded Springs|Jumpluff ex|78|Jumpluff}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|2}}
|-
| 079/071 || {{TCG ID|Secluded Springs|Entei ex|79|Entei}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|2}}
|-
| 080/071 || {{TCG ID|Secluded Springs|Suicune ex|80|Suicune}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|2}}
|-
| 081/071 || {{TCG ID|Secluded Springs|Raikou ex|81|Raikou}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|2}}
|-
| 082/071 || {{TCG ID|Secluded Springs|Poliwrath ex|82|Poliwrath}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|2}}
|-
| 083/071 || {{TCG ID|Secluded Springs|Whitney|83}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}}
|-
| 084/071 || {{TCG ID|Secluded Springs|Traveling Merchant|84}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}}
|-
| 085/071 || {{TCG ID|Secluded Springs|Morty|85}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}}
|-
| 086/071 || {{TCG ID|Secluded Springs|Jumpluff ex|86|Jumpluff}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|2}}
|-
| 087/071 || {{TCG ID|Secluded Springs|Entei ex|87|Entei}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|2}}
|-
| 088/071 || {{TCG ID|Secluded Springs|Raikou ex|88|Raikou}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|2}}
|-
| 089/071 || {{TCG ID|Secluded Springs|Poliwrath ex|89|Poliwrath}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|2}}
|-
| 090/071 || {{TCG ID|Secluded Springs|Suicune ex|90|Suicune}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|3}}
|-
| 091/071 || {{TCG ID|Secluded Springs|Chimchar|91}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Shiny|1}}
|-
| 092/071 || {{TCG ID|Secluded Springs|Monferno|92}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Shiny|1}}
|-
| 093/071 || {{TCG ID|Secluded Springs|Psyduck|93}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}}
|-
| 094/071 || {{TCG ID|Secluded Springs|Golduck|94}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}}
|-
| 095/071 || {{TCG ID|Secluded Springs|Krabby|95}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}}
|-
| 096/071 || {{TCG ID|Secluded Springs|Kingler|96}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}}
|-
| 097/071 || {{TCG ID|Secluded Springs|Pyukumuku|97}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}}
|-
| 098/071 || {{TCG ID|Secluded Springs|Gible|98}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|1}}
|-
| 099/071 || {{TCG ID|Secluded Springs|Gabite|99}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|1}}
|-
| 100/071 || {{TCG ID|Secluded Springs|Paldean Wooper|100}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|1}}
|-
| 101/071 || {{TCG ID|Secluded Springs|Infernape ex|101|Infernape}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Shiny|2}}
|-
| 102/071 || {{TCG ID|Secluded Springs|Mew ex|102|Mew}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Shiny|2}}
|-
| 103/071 || {{TCG ID|Secluded Springs|Garchomp ex|103|Garchomp}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|2}}
|-
| 104/071 || {{TCG ID|Secluded Springs|Paldean Clodsire ex|104|Paldean Clodsire}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|2}}
|-
| 105/071 || {{TCG ID|Secluded Springs|Mantyke|105}} || {{TCG Icon|Water}} || {{Rar/TCGP|Crown|1}}
|}
"""