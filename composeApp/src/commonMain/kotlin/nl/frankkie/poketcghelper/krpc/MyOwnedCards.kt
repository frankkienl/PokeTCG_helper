package nl.frankkie.poketcghelper.krpc

import kotlinx.serialization.Serializable

@Serializable
data class MyOwnedCards(val cardAmounts: Map<Int, Int>)