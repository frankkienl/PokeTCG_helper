package nl.frankkie.poketcghelper.krpc

import kotlin.coroutines.CoroutineContext

class MyPokeCardsServiceImpl(override val coroutineContext: CoroutineContext) : MyPokeCardsService {

    private var _ownedCards: MyOwnedCards = mapOf()

    override suspend fun getOwnedCards(user: MyUser): MyOwnedCards {
        return _ownedCards
    }

    override suspend fun setOwnedCards(user: MyUser, ownedCards: MyOwnedCards) {
        _ownedCards = ownedCards
    }

    override suspend fun setOwnedCard(user: MyUser, cardNumber: Int, ownedAmount: Int) {
        val temp = _ownedCards.toMutableMap()
        temp[cardNumber] = ownedAmount
        _ownedCards = temp.toMap()
    }

    override suspend fun getOwnedCard(user: MyUser, cardNumber: Int): Int {
        _ownedCards[cardNumber]?.let { return it } ?: return 0
    }
}