package nl.frankkie.poketcghelper.krpc

import kotlin.coroutines.CoroutineContext

class MyPokeCardsServiceImpl(val coroutineContext: CoroutineContext) : MyPokeCardsService {

    private var _ownedCards: MyOwnedCards = MyOwnedCards(mapOf())

    override suspend fun getOwnedCards(user: MyUser): MyOwnedCards {
        return _ownedCards
    }

    override suspend fun setOwnedCards(user: MyUser, ownedCards: MyOwnedCards) {
        _ownedCards = ownedCards
    }

    override suspend fun setOwnedCard(user: MyUser, cardNumber: Int, ownedAmount: Int) {
        val temp = _ownedCards.cardAmounts.toMutableMap()
        temp[cardNumber] = ownedAmount
        _ownedCards = MyOwnedCards(temp)
    }

    override suspend fun getOwnedCard(user: MyUser, cardNumber: Int): Int {
        _ownedCards.cardAmounts[cardNumber]?.let { return it } ?: return 0
    }
}