package agent.trade.plugins.tws

import com.ib.client.*
import io.ktor.server.application.*
import org.slf4j.Logger

private var client: TwsClient? = null

fun Application.configureTWS() {
    client = TwsClient(log)
}

//fun Application.reqMarketData(mrkDataRequest: ) {
//    if(client === null) {
//        log.debug("TWS Client is null, cannot execute [test()]")
//        return;
//    }
//
//    //client!!.socket.reqMarketRule()
//}

private class TwsClient(
    private val log: Logger,
    private val signal: EJavaSignal = EJavaSignal(),
    private val eventWrapper: EventWrapper = EventWrapper(),
    private val eClientSocket: EClientSocket = EClientSocket(eventWrapper, signal),
    private var reader: EReader = EReader(eClientSocket, signal),
) {

    private var disconnectInProgress = false

    val events: EventWrapper
        get() = eventWrapper

    val socket: EClientSocket
        get() = eClientSocket;

    init {
        log.debug("Initializing TWS Client")
        onConnect()
    }

    private fun onConnect() {
        if (eClientSocket.isConnected) return

        // connect to TWS
        disconnectInProgress = false
        eClientSocket.eConnect("127.0.0.1", 7496, 0)
        if (eClientSocket.isConnected) {
            log.debug(
                "Connected to Tws server version " +
                        eClientSocket.serverVersion() + " at " +
                        eClientSocket.twsConnectionTime
            )
        }
        reader = EReader(eClientSocket, signal)
        reader.start()
        Thread {
            processMessages()
            val i = 0
            println(i)
        }.start()
    }

    private fun processMessages() {
        while (eClientSocket.isConnected) {
            signal.waitForSignal()
            try {
                reader.processMsgs()
            } catch (e: Exception) {
                error(e)
            }
        }
    }

}