package nl.frankkie.poketcghelper.desktop_utils

/*
 * Scrape data from Bulbapedia
 */

fun main() {
    processData()
}

fun processData() {
    val data = BULBA_DATE
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
            val typeText = when {
                type == "{{TCG Icon|Grass}}" -> {
                    "GRASS"
                }

                type == "{{TCG Icon|Fire}}" -> {
                    "FIRE"
                }

                type == "{{TCG Icon|Water}}" -> {
                    "WATER"
                }

                type == "{{TCG Icon|Lightning}}" -> {
                    "LIGHTNING"
                }

                type == "{{TCG Icon|Psychic}}" -> {
                    "PSYCHIC"
                }

                type == "{{TCG Icon|Fighting}}" -> {
                    "FIGHTING"
                }

                type == "{{TCG Icon|Darkness}}" -> {
                    "DARKNESS"
                }

                else -> {
                    ""
                }
            }
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

                else -> {
                    ""
                }
            }
            // pack
            val packSolgaleo = parts[4].trim()
            val packLunala = parts[5].trim()
            val packText = when {
                packSolgaleo == "{{Yes}}" && packLunala == "{{Yes}}" -> {
                    // Both
                    ""
                }

                packSolgaleo == "{{Yes}}" -> {
                    "SOLGALEO"
                }

                packLunala == "{{Yes}}" -> {
                    "LUNALA"
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
                },
            """.trimIndent()
            )
        }
    }
    result.append("]\n")
    println(result.toString())
}


//https://bulbapedia.bulbagarden.net/wiki/Celestial_Guardians_(TCG_Pocket)
const val BULBA_DATE = """
{{TCG Set List/header|tablecol=E72|bordercol=87D|cellcol=FC7}}
{{TCG Set List/header/packs|Solgaleo|Lunala|cellcol=FC7|setcode=A3}}
|-
| 001/155 || {{TCG ID|Celestial Guardians|Exeggcute|1}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 002/155 || {{TCG ID|Celestial Guardians|Alolan Exeggutor|2}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 003/155 || {{TCG ID|Celestial Guardians|Surskit|3}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 004/155 || {{TCG ID|Celestial Guardians|Masquerain|4}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 005/155 || {{TCG ID|Celestial Guardians|Maractus|5}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 006/155 || {{TCG ID|Celestial Guardians|Karrablast|6}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 007/155 || {{TCG ID|Celestial Guardians|Phantump|7}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 008/155 || {{TCG ID|Celestial Guardians|Trevenant|8}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 009/155 || {{TCG ID|Celestial Guardians|Rowlet|9}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 010/155 || {{TCG ID|Celestial Guardians|Rowlet|10}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 011/155 || {{TCG ID|Celestial Guardians|Dartrix|11}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 012/155 || {{TCG ID|Celestial Guardians|Decidueye ex|12|Decidueye}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|4}} || || {{Yes}}
|-
| 013/155 || {{TCG ID|Celestial Guardians|Grubbin|13}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 014/155 || {{TCG ID|Celestial Guardians|Fomantis|14}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 015/155 || {{TCG ID|Celestial Guardians|Lurantis|15}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 016/155 || {{TCG ID|Celestial Guardians|Morelull|16}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 017/155 || {{TCG ID|Celestial Guardians|Shiinotic|17}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 018/155 || {{TCG ID|Celestial Guardians|Bounsweet|18}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 019/155 || {{TCG ID|Celestial Guardians|Steenee|19}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 020/155 || {{TCG ID|Celestial Guardians|Tsareena|20}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 021/155 || {{TCG ID|Celestial Guardians|Wimpod|21}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 022/155 || {{TCG ID|Celestial Guardians|Golisopod|22}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 023/155 || {{TCG ID|Celestial Guardians|Dhelmise ex|23|Dhelmise}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|4}} || {{Yes}} || 
|-
| 024/155 || {{TCG ID|Celestial Guardians|Tapu Bulu|24}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 025/155 || {{TCG ID|Celestial Guardians|Growlithe|25}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 026/155 || {{TCG ID|Celestial Guardians|Arcanine|26}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 027/155 || {{TCG ID|Celestial Guardians|Alolan Marowak|27}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 028/155 || {{TCG ID|Celestial Guardians|Fletchinder|28}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 029/155 || {{TCG ID|Celestial Guardians|Talonflame|29}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 030/155 || {{TCG ID|Celestial Guardians|Litten|30}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 031/155 || {{TCG ID|Celestial Guardians|Litten|31}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 032/155 || {{TCG ID|Celestial Guardians|Torracat|32}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 033/155 || {{TCG ID|Celestial Guardians|Incineroar ex|33|Incineroar}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|4}} || {{Yes}} || 
|-
| 034/155 || {{TCG ID|Celestial Guardians|Oricorio|34}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 035/155 || {{TCG ID|Celestial Guardians|Salandit|35}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 036/155 || {{TCG ID|Celestial Guardians|Salazzle|36}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 037/155 || {{TCG ID|Celestial Guardians|Turtonator|37}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 038/155 || {{TCG ID|Celestial Guardians|Alolan Sandshrew|38}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 039/155 || {{TCG ID|Celestial Guardians|Alolan Sandslash|39}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 040/155 || {{TCG ID|Celestial Guardians|Alolan Vulpix|40}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 041/155 || {{TCG ID|Celestial Guardians|Alolan Ninetales|41}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 042/155 || {{TCG ID|Celestial Guardians|Shellder|42}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 043/155 || {{TCG ID|Celestial Guardians|Cloyster|43}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 044/155 || {{TCG ID|Celestial Guardians|Lapras|44}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 045/155 || {{TCG ID|Celestial Guardians|Popplio|45}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 046/155 || {{TCG ID|Celestial Guardians|Popplio|46}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 047/155 || {{TCG ID|Celestial Guardians|Brionne|47}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 048/155 || {{TCG ID|Celestial Guardians|Primarina|48}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 049/155 || {{TCG ID|Celestial Guardians|Crabominable ex|49|Crabominable}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|4}} || {{Yes}} || 
|-
| 050/155 || {{TCG ID|Celestial Guardians|Wishiwashi|50}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 051/155 || {{TCG ID|Celestial Guardians|Wishiwashi ex|51|Wishiwashi}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|4}} || || {{Yes}}
|-
| 052/155 || {{TCG ID|Celestial Guardians|Dewpider|52}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 053/155 || {{TCG ID|Celestial Guardians|Araquanid|53}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 054/155 || {{TCG ID|Celestial Guardians|Pyukumuku|54}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 055/155 || {{TCG ID|Celestial Guardians|Bruxish|55}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 056/155 || {{TCG ID|Celestial Guardians|Tapu Fini|56}} || {{TCG Icon|Water}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 057/155 || {{TCG ID|Celestial Guardians|Pikachu|57}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 058/155 || {{TCG ID|Celestial Guardians|Alolan Raichu ex|58|Alolan Raichu}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|4}} || {{Yes}} || 
|-
| 059/155 || {{TCG ID|Celestial Guardians|Alolan Geodude|59}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 060/155 || {{TCG ID|Celestial Guardians|Alolan Graveler|60}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 061/155 || {{TCG ID|Celestial Guardians|Alolan Golem|61}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 062/155 || {{TCG ID|Celestial Guardians|Helioptile|62}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 063/155 || {{TCG ID|Celestial Guardians|Heliolisk|63}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 064/155 || {{TCG ID|Celestial Guardians|Charjabug|64}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 065/155 || {{TCG ID|Celestial Guardians|Vikavolt|65}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 066/155 || {{TCG ID|Celestial Guardians|Oricorio|66}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 067/155 || {{TCG ID|Celestial Guardians|Togedemaru|67}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 068/155 || {{TCG ID|Celestial Guardians|Tapu Koko|68}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 069/155 || {{TCG ID|Celestial Guardians|Mr. Mime|69}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 070/155 || {{TCG ID|Celestial Guardians|Sableye|70}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 071/155 || {{TCG ID|Celestial Guardians|Spoink|71}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 072/155 || {{TCG ID|Celestial Guardians|Grumpig|72}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 073/155 || {{TCG ID|Celestial Guardians|Lunatone|73}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 074/155 || {{TCG ID|Celestial Guardians|Shuppet|74}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 075/155 || {{TCG ID|Celestial Guardians|Banette|75}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 076/155 || {{TCG ID|Celestial Guardians|Oricorio|76}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 077/155 || {{TCG ID|Celestial Guardians|Oricorio|77}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 078/155 || {{TCG ID|Celestial Guardians|Cutiefly|78}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 079/155 || {{TCG ID|Celestial Guardians|Ribombee|79}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 080/155 || {{TCG ID|Celestial Guardians|Comfey|80}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 081/155 || {{TCG ID|Celestial Guardians|Sandygast|81}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 082/155 || {{TCG ID|Celestial Guardians|Palossand|82}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 083/155 || {{TCG ID|Celestial Guardians|Mimikyu|83}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 084/155 || {{TCG ID|Celestial Guardians|Tapu Lele|84}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 085/155 || {{TCG ID|Celestial Guardians|Cosmog|85}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 086/155 || {{TCG ID|Celestial Guardians|Cosmoem|86}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 087/155 || {{TCG ID|Celestial Guardians|Lunala ex|87|Lunala}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|4}} || || {{Yes}}
|-
| 088/155 || {{TCG ID|Celestial Guardians|Necrozma|88}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 089/155 || {{TCG ID|Celestial Guardians|Cubone|89}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 090/155 || {{TCG ID|Celestial Guardians|Makuhita|90}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 091/155 || {{TCG ID|Celestial Guardians|Hariyama|91}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 092/155 || {{TCG ID|Celestial Guardians|Solrock|92}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 093/155 || {{TCG ID|Celestial Guardians|Drilbur|93}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 094/155 || {{TCG ID|Celestial Guardians|Timburr|94}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 095/155 || {{TCG ID|Celestial Guardians|Gurdurr|95}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 096/155 || {{TCG ID|Celestial Guardians|Conkeldurr|96}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 097/155 || {{TCG ID|Celestial Guardians|Crabrawler|97}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 098/155 || {{TCG ID|Celestial Guardians|Rockruff|98}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 099/155 || {{TCG ID|Celestial Guardians|Rockruff|99}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 100/155 || {{TCG ID|Celestial Guardians|Lycanroc|100}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 101/155 || {{TCG ID|Celestial Guardians|Lycanroc|101}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 102/155 || {{TCG ID|Celestial Guardians|Mudbray|102}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 103/155 || {{TCG ID|Celestial Guardians|Mudsdale|103}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 104/155 || {{TCG ID|Celestial Guardians|Passimian ex|104|Passimian}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|4}} || || {{Yes}}
|-
| 105/155 || {{TCG ID|Celestial Guardians|Minior|105}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 106/155 || {{TCG ID|Celestial Guardians|Alolan Rattata|106}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 107/155 || {{TCG ID|Celestial Guardians|Alolan Raticate|107}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 108/155 || {{TCG ID|Celestial Guardians|Alolan Meowth|108}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 109/155 || {{TCG ID|Celestial Guardians|Alolan Persian|109}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 110/155 || {{TCG ID|Celestial Guardians|Alolan Grimer|110}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 111/155 || {{TCG ID|Celestial Guardians|Alolan Muk ex|111|Alolan Muk}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|4}} || || {{Yes}}
|-
| 112/155 || {{TCG ID|Celestial Guardians|Absol|112}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 113/155 || {{TCG ID|Celestial Guardians|Trubbish|113}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 114/155 || {{TCG ID|Celestial Guardians|Garbodor|114}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 115/155 || {{TCG ID|Celestial Guardians|Mareanie|115}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 116/155 || {{TCG ID|Celestial Guardians|Toxapex|116}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 117/155 || {{TCG ID|Celestial Guardians|Alolan Diglett|117}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 118/155 || {{TCG ID|Celestial Guardians|Alolan Dugtrio|118}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 119/155 || {{TCG ID|Celestial Guardians|Excadrill|119}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 120/155 || {{TCG ID|Celestial Guardians|Escavalier|120}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 121/155 || {{TCG ID|Celestial Guardians|Klefki|121}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 122/155 || {{TCG ID|Celestial Guardians|Solgaleo ex|122|Solgaleo}}{{TCGP Icon|ex}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|4}} || {{Yes}} || 
|-
| 123/155 || {{TCG ID|Celestial Guardians|Magearna|123}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 124/155 || {{TCG ID|Celestial Guardians|Drampa|124}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Diamond|3}} || {{Yes}} || 
|-
| 125/155 || {{TCG ID|Celestial Guardians|Jangmo-o|125}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 126/155 || {{TCG ID|Celestial Guardians|Hakamo-o|126}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 127/155 || {{TCG ID|Celestial Guardians|Kommo-o|127}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 128/155 || {{TCG ID|Celestial Guardians|Tauros|128}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 129/155 || {{TCG ID|Celestial Guardians|Skitty|129}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 130/155 || {{TCG ID|Celestial Guardians|Delcatty|130}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 131/155 || {{TCG ID|Celestial Guardians|Fletchling|131}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 132/155 || {{TCG ID|Celestial Guardians|Hawlucha|132}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 133/155 || {{TCG ID|Celestial Guardians|Pikipek|133}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 134/155 || {{TCG ID|Celestial Guardians|Trumbeak|134}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || {{Yes}}
|-
| 135/155 || {{TCG ID|Celestial Guardians|Toucannon|135}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 136/155 || {{TCG ID|Celestial Guardians|Yungoos|136}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 137/155 || {{TCG ID|Celestial Guardians|Gumshoos|137}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || {{Yes}} || 
|-
| 138/155 || {{TCG ID|Celestial Guardians|Stufful|138}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|1}} || || {{Yes}}
|-
| 139/155 || {{TCG ID|Celestial Guardians|Bewear|139}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 140/155 || {{TCG ID|Celestial Guardians|Oranguru|140}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|3}} || || {{Yes}}
|-
| 141/155 || {{TCG ID|Celestial Guardians|Komala|141}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 142/155 || {{TCG ID|Celestial Guardians|Big Malasada|142}} || {{TCG Icon|Item}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 143/155 || {{TCG ID|Celestial Guardians|Fishing Net|143}} || {{TCG Icon|Item}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 144/155 || {{TCG ID|Celestial Guardians|Rare Candy|144}} || {{TCG Icon|Item}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || {{Yes}}
|-
| 145/155 || {{TCG ID|Celestial Guardians|Rotom Dex|145}} || {{TCG Icon|Item}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 146/155 || {{TCG ID|Celestial Guardians|Poison Barb|146}} || {{TCG Icon|Pokémon Tool}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 147/155 || {{TCG ID|Celestial Guardians|Leaf Cape|147}} || {{TCG Icon|Pokémon Tool}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 148/155 || {{TCG ID|Celestial Guardians|Acerola|148}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 149/155 || {{TCG ID|Celestial Guardians|Ilima|149}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 150/155 || {{TCG ID|Celestial Guardians|Kiawe|150}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 151/155 || {{TCG ID|Celestial Guardians|Guzma|151}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} || || {{Yes}}
|-
| 152/155 || {{TCG ID|Celestial Guardians|Lana|152}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 153/155 || {{TCG ID|Celestial Guardians|Sophocles|153}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 154/155 || {{TCG ID|Celestial Guardians|Mallow|154}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 155/155 || {{TCG ID|Celestial Guardians|Lillie|155}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Diamond|2}} || {{Yes}} || 
|-
| 156/155 || {{TCG ID|Celestial Guardians|Alolan Exeggutor|156}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 157/155 || {{TCG ID|Celestial Guardians|Morelull|157}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 158/155 || {{TCG ID|Celestial Guardians|Tsareena|158}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 159/155 || {{TCG ID|Celestial Guardians|Tapu Bulu|159}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 160/155 || {{TCG ID|Celestial Guardians|Alolan Marowak|160}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 161/155 || {{TCG ID|Celestial Guardians|Turtonator|161}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 162/155 || {{TCG ID|Celestial Guardians|Alolan Vulpix|162}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 163/155 || {{TCG ID|Celestial Guardians|Pyukumuku|163}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 164/155 || {{TCG ID|Celestial Guardians|Tapu Fini|164}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 165/155 || {{TCG ID|Celestial Guardians|Oricorio|165}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 166/155 || {{TCG ID|Celestial Guardians|Tapu Koko|166}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 167/155 || {{TCG ID|Celestial Guardians|Cutiefly|167}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 168/155 || {{TCG ID|Celestial Guardians|Comfey|168}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 169/155 || {{TCG ID|Celestial Guardians|Sandygast|169}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 170/155 || {{TCG ID|Celestial Guardians|Tapu Lele|170}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 171/155 || {{TCG ID|Celestial Guardians|Cosmog|171}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 172/155 || {{TCG ID|Celestial Guardians|Rockruff|172}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 173/155 || {{TCG ID|Celestial Guardians|Mudsdale|173}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 174/155 || {{TCG ID|Celestial Guardians|Minior|174}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 175/155 || {{TCG ID|Celestial Guardians|Magearna|175}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 176/155 || {{TCG ID|Celestial Guardians|Drampa|176}} || {{TCG Icon|Dragon}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 177/155 || {{TCG ID|Celestial Guardians|Pikipek|177}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 178/155 || {{TCG ID|Celestial Guardians|Bewear|178}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}} || || {{Yes}}
|-
| 179/155 || {{TCG ID|Celestial Guardians|Komala|179}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Star|1}} || {{Yes}} || 
|-
| 180/155 || {{TCG ID|Celestial Guardians|Decidueye ex|180|Decidueye}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 181/155 || {{TCG ID|Celestial Guardians|Dhelmise ex|181|Dhelmise}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 182/155 || {{TCG ID|Celestial Guardians|Incineroar ex|182|Incineroar}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 183/155 || {{TCG ID|Celestial Guardians|Crabominable ex|183|Crabominable}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 184/155 || {{TCG ID|Celestial Guardians|Wishiwashi ex|184|Wishiwashi}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 185/155 || {{TCG ID|Celestial Guardians|Alolan Raichu ex|185|Alolan Raichu}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 186/155 || {{TCG ID|Celestial Guardians|Lunala ex|186|Lunala}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 187/155 || {{TCG ID|Celestial Guardians|Passimian ex|187|Passimian}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 188/155 || {{TCG ID|Celestial Guardians|Alolan Muk ex|188|Alolan Muk}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 189/155 || {{TCG ID|Celestial Guardians|Solgaleo ex|189|Solgaleo}}{{TCGP Icon|ex}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 190/155 || {{TCG ID|Celestial Guardians|Acerola|190}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 191/155 || {{TCG ID|Celestial Guardians|Ilima|191}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 192/155 || {{TCG ID|Celestial Guardians|Kiawe|192}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 193/155 || {{TCG ID|Celestial Guardians|Guzma|193}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 194/155 || {{TCG ID|Celestial Guardians|Lana|194}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 195/155 || {{TCG ID|Celestial Guardians|Sophocles|195}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 196/155 || {{TCG ID|Celestial Guardians|Mallow|196}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 197/155 || {{TCG ID|Celestial Guardians|Lillie|197}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 198/155 || {{TCG ID|Celestial Guardians|Decidueye ex|198|Decidueye}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 199/155 || {{TCG ID|Celestial Guardians|Dhelmise ex|199|Dhelmise}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 200/155 || {{TCG ID|Celestial Guardians|Incineroar ex|200|Incineroar}}{{TCGP Icon|ex}} || {{TCG Icon|Fire}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 201/155 || {{TCG ID|Celestial Guardians|Crabominable ex|201|Crabominable}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 202/155 || {{TCG ID|Celestial Guardians|Wishiwashi ex|202|Wishiwashi}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 203/155 || {{TCG ID|Celestial Guardians|Alolan Raichu ex|203|Alolan Raichu}}{{TCGP Icon|ex}} || {{TCG Icon|Lightning}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 204/155 || {{TCG ID|Celestial Guardians|Lunala ex|204|Lunala}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 205/155 || {{TCG ID|Celestial Guardians|Passimian ex|205|Passimian}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 206/155 || {{TCG ID|Celestial Guardians|Alolan Muk ex|206|Alolan Muk}}{{TCGP Icon|ex}} || {{TCG Icon|Darkness}} || {{Rar/TCGP|Star|2}} || || {{Yes}}
|-
| 207/155 || {{TCG ID|Celestial Guardians|Solgaleo ex|207|Solgaleo}}{{TCGP Icon|ex}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Star|2}} || {{Yes}} || 
|-
| 208/155 || {{TCG ID|Celestial Guardians|Guzma|208}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|3}} || || {{Yes}}
|-
| 209/155 || {{TCG ID|Celestial Guardians|Lillie|209}} || {{TCG Icon|Supporter}} || {{Rar/TCGP|Star|3}} || {{Yes}} || 
|-
| 210/155 || {{TCG ID|Celestial Guardians|Bulbasaur|210}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 211/155 || {{TCG ID|Celestial Guardians|Ivysaur|211}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 212/155 || {{TCG ID|Celestial Guardians|Venusaur|212}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 213/155 || {{TCG ID|Celestial Guardians|Exeggcute|213}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|1}} || || {{Yes}}
|-
| 214/155 || {{TCG ID|Celestial Guardians|Exeggutor|214}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|1}} || || {{Yes}}
|-
| 215/155 || {{TCG ID|Celestial Guardians|Squirtle|215}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}} || || {{Yes}}
|-
| 216/155 || {{TCG ID|Celestial Guardians|Wartortle|216}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}} || || {{Yes}}
|-
| 217/155 || {{TCG ID|Celestial Guardians|Blastoise|217}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}} || || {{Yes}}
|-
| 218/155 || {{TCG ID|Celestial Guardians|Staryu|218}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 219/155 || {{TCG ID|Celestial Guardians|Starmie|219}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 220/155 || {{TCG ID|Celestial Guardians|Gastly|220}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Shiny|1}} || || {{Yes}}
|-
| 221/155 || {{TCG ID|Celestial Guardians|Haunter|221}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Shiny|1}} || || {{Yes}}
|-
| 222/155 || {{TCG ID|Celestial Guardians|Gengar|222}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Shiny|1}} || || {{Yes}}
|-
| 223/155 || {{TCG ID|Celestial Guardians|Machop|223}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 224/155 || {{TCG ID|Celestial Guardians|Machoke|224}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 225/155 || {{TCG ID|Celestial Guardians|Machamp|225}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 226/155 || {{TCG ID|Celestial Guardians|Cubone|226}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|1}} || || {{Yes}}
|-
| 227/155 || {{TCG ID|Celestial Guardians|Marowak|227}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|1}} || || {{Yes}}
|-
| 228/155 || {{TCG ID|Celestial Guardians|Jigglypuff|228}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 229/155 || {{TCG ID|Celestial Guardians|Wigglytuff|229}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|1}} || {{Yes}} || 
|-
| 230/155 || {{TCG ID|Celestial Guardians|Venusaur ex|230|Venusaur}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|2}} || {{Yes}} || 
|-
| 231/155 || {{TCG ID|Celestial Guardians|Exeggutor ex|231|Exeggutor}}{{TCGP Icon|ex}} || {{TCG Icon|Grass}} || {{Rar/TCGP|Shiny|2}} || || {{Yes}}
|-
| 232/155 || {{TCG ID|Celestial Guardians|Blastoise ex|232|Blastoise}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|2}} || || {{Yes}}
|-
| 233/155 || {{TCG ID|Celestial Guardians|Starmie ex|233|Starmie}}{{TCGP Icon|ex}} || {{TCG Icon|Water}} || {{Rar/TCGP|Shiny|2}} || {{Yes}} || 
|-
| 234/155 || {{TCG ID|Celestial Guardians|Gengar ex|234|Gengar}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Shiny|2}} || || {{Yes}}
|-
| 235/155 || {{TCG ID|Celestial Guardians|Machamp ex|235|Machamp}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|2}} || {{Yes}} || 
|-
| 236/155 || {{TCG ID|Celestial Guardians|Marowak ex|236|Marowak}}{{TCGP Icon|ex}} || {{TCG Icon|Fighting}} || {{Rar/TCGP|Shiny|2}} || || {{Yes}}
|-
| 237/155 || {{TCG ID|Celestial Guardians|Wigglytuff ex|237|Wigglytuff}}{{TCGP Icon|ex}} || {{TCG Icon|Colorless}} || {{Rar/TCGP|Shiny|2}} || {{Yes}} || 
|-
| 238/155 || {{TCG ID|Celestial Guardians|Lunala ex|238|Lunala}}{{TCGP Icon|ex}} || {{TCG Icon|Psychic}} || {{Rar/TCGP|Crown}} || {{Yes}} || {{Yes}}
|-
| 239/155 || {{TCG ID|Celestial Guardians|Solgaleo ex|239|Solgaleo}}{{TCGP Icon|ex}} || {{TCG Icon|Metal}} || {{Rar/TCGP|Crown}} || {{Yes}} || {{Yes}}
|}
"""