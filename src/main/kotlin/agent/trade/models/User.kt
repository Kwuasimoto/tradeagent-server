package agent.trade.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.*

object UserTable: Table("users") {
    private val id = integer("id").autoIncrement()
    val fullName = varchar("full_name", 128)
    val avatar = text("avatar")
    val email = varchar("email", 128)
    val password = text("password")
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(id)
}