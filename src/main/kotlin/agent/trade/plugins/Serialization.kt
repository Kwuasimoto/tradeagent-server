package agent.trade.plugins

import agent.trade.dto.ClientConfigurationRequestDTO
import agent.trade.dto.ClientConfigurationRequestDTOAdapter
import com.google.gson.Gson
import io.ktor.serialization.gson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    GsonConverter(Gson())
    install(ContentNegotiation) {
        gson {
            registerTypeAdapter(ClientConfigurationRequestDTO::class.java, ClientConfigurationRequestDTOAdapter())
            setPrettyPrinting()
            create()
        }
        json()
    }

    routing {
        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}
