package nl.frankkie.poketcghelper.krpc

import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc

@Rpc
interface MyPokeCardsService : RemoteService {
    suspend fun getOwnedCards(): List<Int>
    suspend fun setOwnedCards(ownedCards: List<Int>)
    suspend fun setOwnedCard(card: Int, owned: Boolean)
    suspend fun getOwnedCard(card: Int): Boolean
}