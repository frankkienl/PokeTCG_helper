package nl.frankkie.poketcghelper.krpc

class MyPokeCardsServiceClient : MyPokeCardsService {
    override suspend fun getOwnedCards(user: MyUser): MyOwnedCards {
        return MyOwnedCards(emptyMap<Int, Int>())
    }

    override suspend fun setOwnedCards(user: MyUser, ownedCards: MyOwnedCards) {
        //TODO("Not yet implemented")
    }

    override suspend fun setOwnedCard(user: MyUser, cardNumber: Int, ownedAmount: Int) {
        //TODO("Not yet implemented")
    }

    override suspend fun getOwnedCard(user: MyUser, cardNumber: Int): Int {
        //TODO("Not yet implemented")
        return 0
    }

}

//
//import io.ktor.client.*
//import io.ktor.http.*
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.rpc.krpc.ktor.client.KtorRPCClient
//import kotlinx.rpc.krpc.ktor.client.installRPC
//import kotlinx.rpc.krpc.ktor.client.rpc
//import kotlinx.rpc.krpc.ktor.client.rpcConfig
//import kotlinx.rpc.krpc.serialization.json.json
//import kotlinx.rpc.withService
//
//val isDebug = true
//
//class MyPokeCardsServiceClient(coroutineScope: CoroutineScope) {
//    lateinit var ktorClient: HttpClient
//    lateinit var rpcClient: KtorRPCClient
//    lateinit var pokeCardsService: MyPokeCardsService
//
//    init {
//        coroutineScope.launch {
//            ktorClient = HttpClient {
//                installRPC {
//                    waitForServices = true
//                }
//            }
//
//            rpcClient = ktorClient.rpc {
//                url {
//                    host = if (isDebug) "locahost" else "home.frankkie.nl"
//                    port = if (isDebug) 8080 else 80
//                    encodedPath = "poketcg/api/v1/"
//                }
//
//                rpcConfig {
//                    serialization {
//                        json()
//                    }
//                }
//            }
//
//            pokeCardsService = rpcClient.withService<MyPokeCardsService>()
//        }
//    }
//
//}