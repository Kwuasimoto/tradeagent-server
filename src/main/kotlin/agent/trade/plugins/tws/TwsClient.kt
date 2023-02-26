package agent.trade.plugins.tws

import com.ib.client.EClientSocket
import com.ib.client.EJavaSignal
import com.ib.client.EReader
import io.ktor.server.application.*
import org.slf4j.Logger

fun Application.configureTWS() {
    TwsClient(log)
}

class TwsClient(
    private var log: Logger,
    private val signal: EJavaSignal = EJavaSignal(),
    private val eventWrapper: EventWrapper = EventWrapper(),
    private val socket: EClientSocket = EClientSocket(eventWrapper, signal),
    private var reader: EReader? = EReader(socket, signal),
) {

    private var disconnectInProgress = false

    init {
        log.debug("Initializing Tws Client")
        onConnect()
    }

    private fun onConnect() {
        if (socket.isConnected) return

        // connect to TWS
        disconnectInProgress = false
        socket.eConnect("127.0.0.1", 7496, 0)
        if (socket.isConnected) {
            log.debug(
                "Connected to Tws server version " +
                        socket.serverVersion() + " at " +
                        socket.twsConnectionTime
            )
        }
        reader = EReader(socket, signal)
        reader!!.start()
        Thread {
            processMessages()
            val i = 0
            println(i)
        }.start()
    }

    private fun processMessages() {
        while (socket.isConnected) {
            signal.waitForSignal()
            try {
                reader!!.processMsgs()
            } catch (e: Exception) {
                error(e)
            }
        }
    }
}