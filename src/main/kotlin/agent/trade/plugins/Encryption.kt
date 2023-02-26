package agent.trade.plugins

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import java.security.SecureRandom
import kotlin.random.Random

fun Application.configureEncryption(): Unit {
    /**/
}

/**
 * We keep the receiver param in this function for scoping encryption functions to only be used,
 * within related receivers
 * */
fun Application.configureSessionTransportTransformer(): SessionTransportTransformerMessageAuthentication {
    val hashKeyRange = 64..256;
    val random = SecureRandom()
    val hashKey = ByteArray(Random.nextInt(hashKeyRange.last - hashKeyRange.first + 1) + hashKeyRange.first)
    random.nextBytes(hashKey)
    return SessionTransportTransformerMessageAuthentication(hashKey)
}