package agent.trade.dto

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
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

class ClientConfigurationRequestDTOAdapter : TypeAdapter<ClientConfigurationRequestDTO>() {
    private val gson = GsonBuilder().create()

    override fun write(out: JsonWriter, value: ClientConfigurationRequestDTO?) {
        if (value == null) {
            out.nullValue()
            return
        }
        val jsonString = gson.toJson(value)
        out.jsonValue(jsonString)
    }

    override fun read(reader: JsonReader): ClientConfigurationRequestDTO? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }
        return gson.fromJson(reader, ClientConfigurationRequestDTO::class.java)
    }
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