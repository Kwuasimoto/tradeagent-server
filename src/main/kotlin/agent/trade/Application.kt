package agent.trade

import io.ktor.server.application.*
import io.ktor.server.netty.*
import agent.trade.plugins.*
import agent.trade.plugins.tws.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureDatabases()
    configureSockets()
    configureRouting()
    configureTWS()
}


