package agent.trade.models

import agent.trade.dto.TWSClientConfigurationRequestDTO
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.*
import java.util.UUID


data class TWSClientConfiguration(
    private val id: String,
    private val clientID: Int,
    private val host: String,
    private val port: Int,
) {
    constructor(row: ResultRow) : this(
        id = row[TWSClientConfigurationTable.getID()],
        clientID = row[TWSClientConfigurationTable.getClientID()],
        host = row[TWSClientConfigurationTable.getHost()],
        port = row[TWSClientConfigurationTable.getPort()],
    )

    fun getID(): String {
        return this.id;
    }

    fun getClientID(): Int {
        return this.clientID
    }

    fun getHost(): String {
        return this.host
    }

    fun getPort(): Int {
        return this.port
    }
}

fun TWSClientConfigurationTable.rowToTWSClientConfiguration(row: ResultRow): TWSClientConfiguration {
    return TWSClientConfiguration(row)
}

object TWSClientConfigurationTable : Table("tws_client_configurations") {
    private val id = varchar("id", 36)
    private val clientId = integer("client_id")
    private val host = varchar("host", 128)
    private val port = integer("port")
    private val userID = (varchar("user_id", 36) references UserTable.id)
    override val primaryKey = PrimaryKey(id)

    fun getID(): Column<String> {
        return this.id;
    }

    fun getClientID(): Column<Int> {
        return this.clientId;
    }

    fun getHost(): Column<String> {
        return this.host;
    }

    fun getPort(): Column<Int> {
        return this.port;
    }

    fun getUserID(): Column<String> {
        return this.userID;
    }

    fun getConfigurationsByUserID(user: User): List<TWSClientConfiguration> {
        return transaction {
            TWSClientConfigurationTable.select {
                getUserID() eq user.getID()
            }.map { row -> TWSClientConfiguration(row) }
        }
    }

    fun insertClientConfigurationRequestDTO(data: TWSClientConfigurationRequestDTO): InsertStatement<Number> {
        return transaction {
            TWSClientConfigurationTable.insert {
                it[getClientID()] = data.clientID;
                it[getID()] = UUID.randomUUID().toString()
                it[getHost()] = data.host
                it[getPort()] = data.port
            }
        }
    }
}

fun Application.migrateTWSClientConfigurationTable() {
    // Migrate the table changes to the database
    SchemaUtils.createMissingTablesAndColumns(TWSClientConfigurationTable)
}

val queryTWSClientConfigUserInnerJoin: Pair<User, TWSClientConfiguration>? =
    (UserTable innerJoin TWSClientConfigurationTable)
        .select { UserTable.getID() eq TWSClientConfigurationTable.getUserID() }
        .map {
            val user = UserTable.rowToUser(it)
            val config = TWSClientConfigurationTable.rowToTWSClientConfiguration(it)
            Pair(user, config)
        }
        .take(1)
        .singleOrNull()