package nl.frankkie.poketcghelper.krpc

import kotlinx.serialization.Serializable

@Serializable
data class MyUser(
    val username: String,
    val ownedCards: MyOwnedCards
)