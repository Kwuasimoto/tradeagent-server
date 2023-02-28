package agent.trade.dto

import kotlinx.serialization.Serializable

enum class MarketClientProviders {
    TWS()
}

@Serializable
sealed interface ClientConfigurationRequestDTO {
    val clientID: Int?
    val host: String
    val port: Int
    val type: Int
}

@Serializable
data class TWSClientConfigurationRequestDTO(
    override val clientID: Int,
    override val host: String,
    override val port: Int,
    override val type: Int
) : ClientConfigurationRequestDTO {
    constructor(request: ClientConfigurationRequestDTO) :
            this(request.clientID!!, request.host, request.port, request.type) {
    }
}