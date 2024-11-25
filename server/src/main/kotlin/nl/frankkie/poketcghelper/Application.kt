package nl.frankkie.poketcghelper

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.rpc.krpc.ktor.server.RPC
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import nl.frankkie.poketcghelper.krpc.MyPokeCardsService
import nl.frankkie.poketcghelper.krpc.MyPokeCardsServiceImpl
import java.io.File

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(RPC)

    routing {
        staticResources("/", "composeApp")

//        get("/") {
//            call.respondText("Ktor: ${Greeting().greet()}")
//        }

        rpc("/rpc") {
            rpcConfig {
                serialization {
                    json()
                }
            }

            registerService<MyPokeCardsService> { ctx -> MyPokeCardsServiceImpl(ctx)}
        }
    }
}