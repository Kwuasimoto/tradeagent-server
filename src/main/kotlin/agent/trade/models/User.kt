package agent.trade.models

import agent.trade.dto.UserLoginDTO
import agent.trade.dto.UserRegistrationDTO
import io.ktor.server.application.*
import net.jpountz.lz4.LZ4Factory
import net.jpountz.xxhash.XXHashFactory
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.*
import java.nio.charset.StandardCharsets
import java.time.*
import java.util.UUID


data class User(
    private val id: String,
    private val username: String,
    private val fullName: String,
    private val avatar: String,
    private val email: String,
    private val password: String,
    private val createdAt: LocalDateTime,
) {
    constructor(row: ResultRow) : this(
        id = row[UserTable.getID()],
        username = row[UserTable.getUsername()],
        email = row[UserTable.getEmail()],
        password = row[UserTable.getPassword()],
        createdAt = row[UserTable.getCreatedAt()],
        fullName = "undefined",
        avatar = "undefined",
    )

    fun getID(): String {
        return this.id;
    }

    fun getUsername(): String {
        return this.username
    }

    fun getFullName(): String {
        return this.fullName
    }

    fun getAvatar(): String {
        return this.avatar
    }

    fun getEmail(): String {
        return this.email
    }

    fun getPassword(): String {
        return this.password
    }

    fun getCreatedAt(): LocalDateTime {
        return this.createdAt
    }
}

fun UserTable.rowToUser(row: ResultRow): User {
    return User(row)
}

object UserTable : Table("users") {
    val id = varchar("id", 36)
    private val fullName = varchar("full_name", 128).nullable()
    private val username = varchar("username", 64)
    private val avatar = text("avatar").nullable()
    private val email = varchar("email", 128)
    private val password = text("password")
    private val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(id)

    fun getID(): Column<String> {
        return this.id;
    }

    fun getUsername(): Column<String> {
        return this.username;
    }

    fun getEmail(): Column<String> {
        return this.email;
    }

    fun getPassword(): Column<String> {
        return this.password;
    }

    fun getCreatedAt(): Column<LocalDateTime> {
        return this.createdAt;
    }
}

fun Application.migrateUserTable() {
    // Migrate the table changes to the database

    SchemaUtils.createMissingTablesAndColumns(UserTable)

}

fun Application.encryptPassword(password: String): String {
    val xxHash32 = XXHashFactory.fastestInstance().hash32()
    val xxHash64 = XXHashFactory.fastestInstance().hash64()
    val lz4 = LZ4Factory.fastestInstance().fastCompressor()

    // Compress the password using LZ4
    val compressedPassword = lz4.compress(password.toByteArray(StandardCharsets.UTF_8))

    // Hash the compressed password using XXHash32 and XXHash64
    val hash32 = xxHash32.hash(compressedPassword, 0, compressedPassword.size, 0)
    val hash64 = xxHash64.hash(compressedPassword, 0, compressedPassword.size, 0)

    // Return the concatenated hash values as a string
    return "$hash32$hash64"
}

fun Application.getUser(userLoginDTO: UserLoginDTO): User {
    var user: User? = null

    transaction {
        user = User(
            UserTable.select {
                UserTable.getUsername().eq(userLoginDTO.username)
            }.limit(1).single()
        )

    }

    if (user == null) {
        throw NullPointerException("Trying to fetch user with username [${userLoginDTO.username}] returned null")
    }

    return user!!
}

fun Application.insertUser(userRegistrationDTO: UserRegistrationDTO): User {
    val userID = UUID.randomUUID().toString();
    var user: User? = null;

    transaction {
        UserTable.insert {
            it[getID()] = userID
            it[getUsername()] = userRegistrationDTO.username
            it[getEmail()] = userRegistrationDTO.email
            it[getPassword()] = encryptPassword(userRegistrationDTO.password)
        }

        // Return the newly created user's information
        user = UserTable.select { UserTable.getID().eq(userID) }
            .map { User(it) }
            .single()
    }

    if (user == null) {
        throw NullPointerException("User Fetched from User Table with [$userID] is null!")
    }

    return user!!;
}