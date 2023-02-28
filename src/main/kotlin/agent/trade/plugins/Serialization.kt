package agent.trade.plugins

import agent.trade.dto.ClientConfigurationRequestDTO
import agent.trade.dto.ClientConfigurationRequestDTOAdapter
import io.ktor.serialization.gson.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            registerTypeAdapter(ClientConfigurationRequestDTO::class.java, ClientConfigurationRequestDTOAdapter())
        }
        json()
    }
    routing {
        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}
