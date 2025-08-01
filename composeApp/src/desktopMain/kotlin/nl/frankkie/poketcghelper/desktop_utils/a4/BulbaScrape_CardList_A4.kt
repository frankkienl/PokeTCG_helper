package nl.frankkie.poketcghelper.desktop_utils.a4

import nl.frankkie.poketcghelper.desktop_utils.a3.BULBA_DATE

/*
 * Scrape data from Bulbapedia
 */

fun main() {
    processData_A4()
}

fun processData_A4() {
    val data = BULBA_DATA_A4
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
            // pack
            val packHoOh = parts[4].trim()
            val packLugia = parts[5].trim()
            val packText = when {
                packHoOh == "{{Yes}}" && packLugia == "{{Yes}}" -> {
                    // Both
                    ""
                }

                packHoOh == "{{Yes}}" -> {
                    "HO_OH"
                }

                packLugia == "{{Yes}}" -> {
                    "LUGIA"
                }

                else -> {
                    // No pack
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
                    "packId": "$packText",
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
const val BULBA_DATA_A4 = """
|-
| 001/161 || {{TCG ID|Wisdom of Sea and Sky|Oddish|1}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 002/161 || {{TCG ID|Wisdom of Sea and Sky|Gloom|2}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 003/161 || {{TCG ID|Wisdom of Sea and Sky|Bellossom|3}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 004/161 || {{TCG ID|Wisdom of Sea and Sky|Tangela|4}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 005/161 || {{TCG ID|Wisdom of Sea and Sky|Tangrowth|5}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 006/161 || {{TCG ID|Wisdom of Sea and Sky|Scyther|6}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 007/161 || {{TCG ID|Wisdom of Sea and Sky|Pinsir|7}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 008/161 || {{TCG ID|Wisdom of Sea and Sky|Chikorita|8}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 009/161 || {{TCG ID|Wisdom of Sea and Sky|Bayleef|9}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 010/161 || {{TCG ID|Wisdom of Sea and Sky|Meganium|10}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 011/161 || {{TCG ID|Wisdom of Sea and Sky|Ledyba|11}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 012/161 || {{TCG ID|Wisdom of Sea and Sky|Ledian|12}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 013/161 || {{TCG ID|Wisdom of Sea and Sky|Hoppip|13}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 014/161 || {{TCG ID|Wisdom of Sea and Sky|Skiploom|14}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 015/161 || {{TCG ID|Wisdom of Sea and Sky|Jumpluff|15}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 016/161 || {{TCG ID|Wisdom of Sea and Sky|Sunkern|16}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 017/161 || {{TCG ID|Wisdom of Sea and Sky|Sunflora|17}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 018/161 || {{TCG ID|Wisdom of Sea and Sky|Yanma|18}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 019/161 || {{TCG ID|Wisdom of Sea and Sky|Yanmega|19}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 020/161 || {{TCG ID|Wisdom of Sea and Sky|Pineco|20}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 021/161 || {{TCG ID|Wisdom of Sea and Sky|Shuckle ex|21|Shuckle}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|4}} ||  || {{Yes}}
|-
| 022/161 || {{TCG ID|Wisdom of Sea and Sky|Heracross|22}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 023/161 || {{TCG ID|Wisdom of Sea and Sky|Cherubi|23}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 024/161 || {{TCG ID|Wisdom of Sea and Sky|Cherrim|24}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 025/161 || {{TCG ID|Wisdom of Sea and Sky|Vulpix|25}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 026/161 || {{TCG ID|Wisdom of Sea and Sky|Ninetales|26}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 027/161 || {{TCG ID|Wisdom of Sea and Sky|Cyndaquil|27}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 028/161 || {{TCG ID|Wisdom of Sea and Sky|Quilava|28}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 029/161 || {{TCG ID|Wisdom of Sea and Sky|Typhlosion|29}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 030/161 || {{TCG ID|Wisdom of Sea and Sky|Slugma|30}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 031/161 || {{TCG ID|Wisdom of Sea and Sky|Magcargo|31}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 032/161 || {{TCG ID|Wisdom of Sea and Sky|Magby|32}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 033/161 || {{TCG ID|Wisdom of Sea and Sky|Entei|33}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 034/161 || {{TCG ID|Wisdom of Sea and Sky|Ho-Oh ex|34|Ho-Oh}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|4}} || {{Yes}} || 
|-
| 035/161 || {{TCG ID|Wisdom of Sea and Sky|Darumaka|35}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 036/161 || {{TCG ID|Wisdom of Sea and Sky|Darmanitan|36}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 037/161 || {{TCG ID|Wisdom of Sea and Sky|Heatmor|37}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 038/161 || {{TCG ID|Wisdom of Sea and Sky|Poliwag|38}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 039/161 || {{TCG ID|Wisdom of Sea and Sky|Poliwhirl|39}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 040/161 || {{TCG ID|Wisdom of Sea and Sky|Politoed|40}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 041/161 || {{TCG ID|Wisdom of Sea and Sky|Horsea|41}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 042/161 || {{TCG ID|Wisdom of Sea and Sky|Seadra|42}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 043/161 || {{TCG ID|Wisdom of Sea and Sky|Kingdra ex|43|Kingdra}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|4}} ||  || {{Yes}}
|-
| 044/161 || {{TCG ID|Wisdom of Sea and Sky|Magikarp|44}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 045/161 || {{TCG ID|Wisdom of Sea and Sky|Gyarados|45}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 046/161 || {{TCG ID|Wisdom of Sea and Sky|Totodile|46}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 047/161 || {{TCG ID|Wisdom of Sea and Sky|Croconaw|47}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 048/161 || {{TCG ID|Wisdom of Sea and Sky|Feraligatr|48}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 049/161 || {{TCG ID|Wisdom of Sea and Sky|Marill|49}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 050/161 || {{TCG ID|Wisdom of Sea and Sky|Azumarill|50}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 051/161 || {{TCG ID|Wisdom of Sea and Sky|Wooper|51}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 052/161 || {{TCG ID|Wisdom of Sea and Sky|Quagsire|52}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 053/161 || {{TCG ID|Wisdom of Sea and Sky|Qwilfish|53}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 054/161 || {{TCG ID|Wisdom of Sea and Sky|Corsola|54}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 055/161 || {{TCG ID|Wisdom of Sea and Sky|Remoraid|55}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 056/161 || {{TCG ID|Wisdom of Sea and Sky|Octillery|56}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 057/161 || {{TCG ID|Wisdom of Sea and Sky|Delibird|57}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 058/161 || {{TCG ID|Wisdom of Sea and Sky|Mantine|58}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 059/161 || {{TCG ID|Wisdom of Sea and Sky|Suicune|59}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 060/161 || {{TCG ID|Wisdom of Sea and Sky|Corphish|60}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 061/161 || {{TCG ID|Wisdom of Sea and Sky|Crawdaunt|61}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 062/161 || {{TCG ID|Wisdom of Sea and Sky|Ducklett|62}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 063/161 || {{TCG ID|Wisdom of Sea and Sky|Swanna|63}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 064/161 || {{TCG ID|Wisdom of Sea and Sky|Chinchou|64}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 065/161 || {{TCG ID|Wisdom of Sea and Sky|Lanturn ex|65|Lanturn}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|4}} ||  || {{Yes}}
|-
| 066/161 || {{TCG ID|Wisdom of Sea and Sky|Pichu|66}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 067/161 || {{TCG ID|Wisdom of Sea and Sky|Mareep|67}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 068/161 || {{TCG ID|Wisdom of Sea and Sky|Flaaffy|68}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 069/161 || {{TCG ID|Wisdom of Sea and Sky|Ampharos|69}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 070/161 || {{TCG ID|Wisdom of Sea and Sky|Elekid|70}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 071/161 || {{TCG ID|Wisdom of Sea and Sky|Raikou|71}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 072/161 || {{TCG ID|Wisdom of Sea and Sky|Emolga|72}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 073/161 || {{TCG ID|Wisdom of Sea and Sky|Slowpoke|73}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 074/161 || {{TCG ID|Wisdom of Sea and Sky|Slowking|74}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 075/161 || {{TCG ID|Wisdom of Sea and Sky|Smoochum|75}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 076/161 || {{TCG ID|Wisdom of Sea and Sky|Jynx|76}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 077/161 || {{TCG ID|Wisdom of Sea and Sky|Cleffa|77}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 078/161 || {{TCG ID|Wisdom of Sea and Sky|Togepi|78}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 079/161 || {{TCG ID|Wisdom of Sea and Sky|Togetic|79}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 080/161 || {{TCG ID|Wisdom of Sea and Sky|Togekiss|80}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 081/161 || {{TCG ID|Wisdom of Sea and Sky|Natu|81}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 082/161 || {{TCG ID|Wisdom of Sea and Sky|Xatu|82}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 083/161 || {{TCG ID|Wisdom of Sea and Sky|Espeon ex|83|Espeon}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|4}} ||  || {{Yes}}
|-
| 084/161 || {{TCG ID|Wisdom of Sea and Sky|Unown|84}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 085/161 || {{TCG ID|Wisdom of Sea and Sky|Unown|85}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 086/161 || {{TCG ID|Wisdom of Sea and Sky|Wobbuffet|86}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 087/161 || {{TCG ID|Wisdom of Sea and Sky|Girafarig|87}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 088/161 || {{TCG ID|Wisdom of Sea and Sky|Snubbull|88}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 089/161 || {{TCG ID|Wisdom of Sea and Sky|Granbull|89}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 090/161 || {{TCG ID|Wisdom of Sea and Sky|Munna|90}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 091/161 || {{TCG ID|Wisdom of Sea and Sky|Musharna|91}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 092/161 || {{TCG ID|Wisdom of Sea and Sky|Onix|92}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 093/161 || {{TCG ID|Wisdom of Sea and Sky|Sudowoodo|93}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 094/161 || {{TCG ID|Wisdom of Sea and Sky|Gligar|94}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 095/161 || {{TCG ID|Wisdom of Sea and Sky|Gliscor|95}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 096/161 || {{TCG ID|Wisdom of Sea and Sky|Swinub|96}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 097/161 || {{TCG ID|Wisdom of Sea and Sky|Piloswine|97}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 098/161 || {{TCG ID|Wisdom of Sea and Sky|Mamoswine|98}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 099/161 || {{TCG ID|Wisdom of Sea and Sky|Phanpy|99}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 100/161 || {{TCG ID|Wisdom of Sea and Sky|Donphan ex|100|Donphan}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|4}} || {{Yes}} || 
|-
| 101/161 || {{TCG ID|Wisdom of Sea and Sky|Tyrogue|101}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 102/161 || {{TCG ID|Wisdom of Sea and Sky|Hitmontop|102}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 103/161 || {{TCG ID|Wisdom of Sea and Sky|Larvitar|103}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 104/161 || {{TCG ID|Wisdom of Sea and Sky|Pupitar|104}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 105/161 || {{TCG ID|Wisdom of Sea and Sky|Binacle|105}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 106/161 || {{TCG ID|Wisdom of Sea and Sky|Barbaracle|106}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 107/161 || {{TCG ID|Wisdom of Sea and Sky|Zubat|107}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 108/161 || {{TCG ID|Wisdom of Sea and Sky|Golbat|108}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 109/161 || {{TCG ID|Wisdom of Sea and Sky|Crobat ex|109|Crobat}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|4}} || {{Yes}} || 
|-
| 110/161 || {{TCG ID|Wisdom of Sea and Sky|Spinarak|110}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 111/161 || {{TCG ID|Wisdom of Sea and Sky|Ariados|111}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 112/161 || {{TCG ID|Wisdom of Sea and Sky|Umbreon ex|112|Umbreon}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|4}} || {{Yes}} || 
|-
| 113/161 || {{TCG ID|Wisdom of Sea and Sky|Murkrow|113}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 114/161 || {{TCG ID|Wisdom of Sea and Sky|Honchkrow|114}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 115/161 || {{TCG ID|Wisdom of Sea and Sky|Sneasel|115}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 116/161 || {{TCG ID|Wisdom of Sea and Sky|Weavile|116}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 117/161 || {{TCG ID|Wisdom of Sea and Sky|Houndour|117}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 118/161 || {{TCG ID|Wisdom of Sea and Sky|Houndoom|118}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 119/161 || {{TCG ID|Wisdom of Sea and Sky|Tyranitar|119}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 120/161 || {{TCG ID|Wisdom of Sea and Sky|Absol|120}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 121/161 || {{TCG ID|Wisdom of Sea and Sky|Forretress|121}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 122/161 || {{TCG ID|Wisdom of Sea and Sky|Steelix|122}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 123/161 || {{TCG ID|Wisdom of Sea and Sky|Scizor|123}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 124/161 || {{TCG ID|Wisdom of Sea and Sky|Skarmory ex|124|Skarmory}}{{TCGP Icon|ex}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|4}} || {{Yes}} || 
|-
| 125/161 || {{TCG ID|Wisdom of Sea and Sky|Mawile|125}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 126/161 || {{TCG ID|Wisdom of Sea and Sky|Klink|126}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 127/161 || {{TCG ID|Wisdom of Sea and Sky|Klang|127}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 128/161 || {{TCG ID|Wisdom of Sea and Sky|Klinklang|128}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 129/161 || {{TCG ID|Wisdom of Sea and Sky|Spearow|129}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 130/161 || {{TCG ID|Wisdom of Sea and Sky|Fearow|130}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 131/161 || {{TCG ID|Wisdom of Sea and Sky|Chansey|131}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 132/161 || {{TCG ID|Wisdom of Sea and Sky|Blissey|132}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 133/161 || {{TCG ID|Wisdom of Sea and Sky|Kangaskhan|133}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 134/161 || {{TCG ID|Wisdom of Sea and Sky|Eevee|134}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 135/161 || {{TCG ID|Wisdom of Sea and Sky|Porygon|135}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} ||  || {{Yes}}
|-
| 136/161 || {{TCG ID|Wisdom of Sea and Sky|Porygon2|136}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 137/161 || {{TCG ID|Wisdom of Sea and Sky|Porygon-Z|137}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|3}} ||  || {{Yes}}
|-
| 138/161 || {{TCG ID|Wisdom of Sea and Sky|Sentret|138}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 139/161 || {{TCG ID|Wisdom of Sea and Sky|Furret|139}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 140/161 || {{TCG ID|Wisdom of Sea and Sky|Hoothoot|140}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 141/161 || {{TCG ID|Wisdom of Sea and Sky|Noctowl|141}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 142/161 || {{TCG ID|Wisdom of Sea and Sky|Aipom|142}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 143/161 || {{TCG ID|Wisdom of Sea and Sky|Ambipom|143}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 144/161 || {{TCG ID|Wisdom of Sea and Sky|Dunsparce|144}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 145/161 || {{TCG ID|Wisdom of Sea and Sky|Teddiursa|145}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 146/161 || {{TCG ID|Wisdom of Sea and Sky|Ursaring|146}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 147/161 || {{TCG ID|Wisdom of Sea and Sky|Stantler|147}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 148/161 || {{TCG ID|Wisdom of Sea and Sky|Smeargle|148}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 149/161 || {{TCG ID|Wisdom of Sea and Sky|Lugia ex|149|Lugia}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|4}} ||  || {{Yes}}
|-
| 150/161 || {{TCG ID|Wisdom of Sea and Sky|Bouffalant|150}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 151/161 || {{TCG ID|Wisdom of Sea and Sky|Elemental Switch|151}} || {{TCG Icon|Item}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 152/161 || {{TCG ID|Wisdom of Sea and Sky|Squirt Bottle|152}} || {{TCG Icon|Item}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 153/161 || {{TCG ID|Wisdom of Sea and Sky|Steel Apron|153}} || {{TCG Icon|Pokémon Tool}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 154/161 || {{TCG ID|Wisdom of Sea and Sky|Dark Pendant|154}} || {{TCG Icon|Pokémon Tool}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 155/161 || {{TCG ID|Wisdom of Sea and Sky|Rescue Scarf|155}} || {{TCG Icon|Pokémon Tool}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 156/161 || {{TCG ID|Wisdom of Sea and Sky|Will|156}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 157/161 || {{TCG ID|Wisdom of Sea and Sky|Lyra|157}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 158/161 || {{TCG ID|Wisdom of Sea and Sky|Silver|158}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 159/161 || {{TCG ID|Wisdom of Sea and Sky|Fisher|159}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} ||  || {{Yes}}
|-
| 160/161 || {{TCG ID|Wisdom of Sea and Sky|Jasmine|160}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 161/161 || {{TCG ID|Wisdom of Sea and Sky|Hiker|161}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 162/161 || {{TCG ID|Wisdom of Sea and Sky|Chikorita|162}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|1}} ||  || {{Yes}}
|-
| 163/161 || {{TCG ID|Wisdom of Sea and Sky|Bellossom|163}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|1}} ||  || {{Yes}}
|-
| 164/161 || {{TCG ID|Wisdom of Sea and Sky|Heracross|164}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 165/161 || {{TCG ID|Wisdom of Sea and Sky|Cyndaquil|165}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|1}} ||  || {{Yes}}
|-
| 166/161 || {{TCG ID|Wisdom of Sea and Sky|Magby|166}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 167/161 || {{TCG ID|Wisdom of Sea and Sky|Totodile|167}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 168/161 || {{TCG ID|Wisdom of Sea and Sky|Qwilfish|168}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|1}} ||  || {{Yes}}
|-
| 169/161 || {{TCG ID|Wisdom of Sea and Sky|Octillery|169}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|1}} ||  || {{Yes}}
|-
| 170/161 || {{TCG ID|Wisdom of Sea and Sky|Delibird|170}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 171/161 || {{TCG ID|Wisdom of Sea and Sky|Pichu|171}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|1}} ||  || {{Yes}}
|-
| 172/161 || {{TCG ID|Wisdom of Sea and Sky|Ampharos|172}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|1}} ||  || {{Yes}}
|-
| 173/161 || {{TCG ID|Wisdom of Sea and Sky|Togepi|173}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 174/161 || {{TCG ID|Wisdom of Sea and Sky|Xatu|174}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|1}} ||  || {{Yes}}
|-
| 175/161 || {{TCG ID|Wisdom of Sea and Sky|Wobbuffet|175}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|1}} ||  || {{Yes}}
|-
| 176/161 || {{TCG ID|Wisdom of Sea and Sky|Gligar|176}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 177/161 || {{TCG ID|Wisdom of Sea and Sky|Spinarak|177}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 178/161 || {{TCG ID|Wisdom of Sea and Sky|Murkrow|178}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|1}} ||  || {{Yes}}
|-
| 179/161 || {{TCG ID|Wisdom of Sea and Sky|Tyranitar|179}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 180/161 || {{TCG ID|Wisdom of Sea and Sky|Scizor|180}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Star|1}} ||  || {{Yes}}
|-
| 181/161 || {{TCG ID|Wisdom of Sea and Sky|Sentret|181}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 182/161 || {{TCG ID|Wisdom of Sea and Sky|Hoothoot|182}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 183/161 || {{TCG ID|Wisdom of Sea and Sky|Stantler|183}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 184/161 || {{TCG ID|Wisdom of Sea and Sky|Smeargle|184}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}}||  || {{Yes}}
|-
| 185/161 || {{TCG ID|Wisdom of Sea and Sky|Blissey|185}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 186/161 || {{TCG ID|Wisdom of Sea and Sky|Shuckle ex|186|Shuckle}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 187/161 || {{TCG ID|Wisdom of Sea and Sky|Ho-Oh ex|187|Ho-Oh}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 188/161 || {{TCG ID|Wisdom of Sea and Sky|Kingdra ex|188|Kingdra}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 189/161 || {{TCG ID|Wisdom of Sea and Sky|Lanturn ex|189|Lanturn}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 190/161 || {{TCG ID|Wisdom of Sea and Sky|Espeon ex|190|Espeon}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 191/161 || {{TCG ID|Wisdom of Sea and Sky|Donphan ex|191|Donphan}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 192/161 || {{TCG ID|Wisdom of Sea and Sky|Crobat ex|192|Crobat}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 193/161 || {{TCG ID|Wisdom of Sea and Sky|Umbreon ex|193|Umbreon}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 194/161 || {{TCG ID|Wisdom of Sea and Sky|Skarmory ex|194|Skarmory}}{{TCGP Icon|ex}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 195/161 || {{TCG ID|Wisdom of Sea and Sky|Lugia ex|195|Lugia}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 196/161 || {{TCG ID|Wisdom of Sea and Sky|Will|196}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 197/161 || {{TCG ID|Wisdom of Sea and Sky|Lyra|197}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 198/161 || {{TCG ID|Wisdom of Sea and Sky|Silver|198}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 199/161 || {{TCG ID|Wisdom of Sea and Sky|Fisher|199}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 200/161 || {{TCG ID|Wisdom of Sea and Sky|Jasmine|200}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 201/161 || {{TCG ID|Wisdom of Sea and Sky|Hiker|201}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 202/161 || {{TCG ID|Wisdom of Sea and Sky|Shuckle ex|202|Shuckle}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 203/161 || {{TCG ID|Wisdom of Sea and Sky|Kingdra ex|203|Kingdra}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 204/161 || {{TCG ID|Wisdom of Sea and Sky|Lanturn ex|204|Lanturn}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 205/161 || {{TCG ID|Wisdom of Sea and Sky|Espeon ex|205|Espeon}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|2}} ||  || {{Yes}}
|-
| 206/161 || {{TCG ID|Wisdom of Sea and Sky|Donphan ex|206|Donphan}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 207/161 || {{TCG ID|Wisdom of Sea and Sky|Crobat ex|207|Crobat}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 208/161 || {{TCG ID|Wisdom of Sea and Sky|Umbreon ex|208|Umbreon}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 209/161 || {{TCG ID|Wisdom of Sea and Sky|Skarmory ex|209|Skarmory}}{{TCGP Icon|ex}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 210/161 || {{TCG ID|Wisdom of Sea and Sky|Ho-Oh ex|210|Ho-Oh}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|3}} || {{Yes}} || 
|-
| 211/161 || {{TCG ID|Wisdom of Sea and Sky|Lugia ex|211|Lugia}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|3}} ||  || {{Yes}}
|-
| 212/161 || {{TCG ID|Wisdom of Sea and Sky|Yanma|212}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 213/161 || {{TCG ID|Wisdom of Sea and Sky|Flareon|213}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 214/161 || {{TCG ID|Wisdom of Sea and Sky|Magikarp|214}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}} ||  || {{Yes}}
|-
| 215/161 || {{TCG ID|Wisdom of Sea and Sky|Gyarados|215}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}} ||  || {{Yes}}
|-
| 216/161 || {{TCG ID|Wisdom of Sea and Sky|Vaporeon|216}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}} ||  || {{Yes}}
|-
| 217/161 || {{TCG ID|Wisdom of Sea and Sky|Magnemite|217}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Shiny|1}} ||  || {{Yes}}
|-
| 218/161 || {{TCG ID|Wisdom of Sea and Sky|Magneton|218}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Shiny|1}} ||  || {{Yes}}
|-
| 219/161 || {{TCG ID|Wisdom of Sea and Sky|Jolteon|219}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Shiny|1}} ||  || {{Yes}}
|-
| 220/161 || {{TCG ID|Wisdom of Sea and Sky|Misdreavus|220}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 221/161 || {{TCG ID|Wisdom of Sea and Sky|Mankey|221}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 222/161 || {{TCG ID|Wisdom of Sea and Sky|Primeape|222}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 223/161 || {{TCG ID|Wisdom of Sea and Sky|Nidoran♀|223}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|1}} ||  || {{Yes}}
|-
| 224/161 || {{TCG ID|Wisdom of Sea and Sky|Nidorina|224}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|1}} ||  || {{Yes}}
|-
| 225/161 || {{TCG ID|Wisdom of Sea and Sky|Nidoqueen|225}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|1}} ||  || {{Yes}}
|-
| 226/161 || {{TCG ID|Wisdom of Sea and Sky|Nidoran♂|226}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 227/161 || {{TCG ID|Wisdom of Sea and Sky|Nidorino|227}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 228/161 || {{TCG ID|Wisdom of Sea and Sky|Nidoking|228}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 229/161 || {{TCG ID|Wisdom of Sea and Sky|Sneasel|229}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|1}} ||  || {{Yes}}
|-
| 230/161 || {{TCG ID|Wisdom of Sea and Sky|Lickitung|230}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 231/161 || {{TCG ID|Wisdom of Sea and Sky|Eevee|231}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 232/161 || {{TCG ID|Wisdom of Sea and Sky|Yanmega ex|232|Yanmega}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|2}} || {{Yes}} || 
|-
| 233/161 || {{TCG ID|Wisdom of Sea and Sky|Leafeon ex|233|Leafeon}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|2}} || {{Yes}} || 
|-
| 234/161 || {{TCG ID|Wisdom of Sea and Sky|Gyarados ex|234|Gyarados}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|2}} ||  || {{Yes}}
|-
| 235/161 || {{TCG ID|Wisdom of Sea and Sky|Glaceon ex|235|Glaceon}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|2}} ||  || {{Yes}}
|-
| 236/161 || {{TCG ID|Wisdom of Sea and Sky|Pachirisu ex|236|Pachirisu}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Shiny|2}} ||  || {{Yes}}
|-
| 237/161 || {{TCG ID|Wisdom of Sea and Sky|Mismagius ex|237|Mismagius}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Shiny|2}} || {{Yes}} || 
|-
| 238/161 || {{TCG ID|Wisdom of Sea and Sky|Weavile ex|238|Weavile}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Shiny|2}} ||  || {{Yes}}
|-
| 239/161 || {{TCG ID|Wisdom of Sea and Sky|Lickilicky ex|239|Lickilicky}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|2}} || {{Yes}} || 
|-
| 240/161 || {{TCG ID|Wisdom of Sea and Sky|Ho-Oh ex|240|Ho-Oh}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Crown|1}} || {{Yes}} || {{Yes}}
|-
| 241/161 || {{TCG ID|Wisdom of Sea and Sky|Lugia ex|241|Lugia}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Crown|1}} || {{Yes}} || {{Yes}}
"""