package nl.frankkie.poketcghelper.krpc

//import kotlinx.rpc.RemoteService
//import kotlinx.rpc.annotations.Rpc

//@Rpc
interface MyPokeCardsService /*: RemoteService */ {
    suspend fun getOwnedCards(user: MyUser): MyOwnedCards
    suspend fun setOwnedCards(user: MyUser, ownedCards: MyOwnedCards)
    suspend fun setOwnedCard(user: MyUser, cardNumber: Int, ownedAmount: Int)
    suspend fun getOwnedCard(user: MyUser, cardNumber: Int): Int
}