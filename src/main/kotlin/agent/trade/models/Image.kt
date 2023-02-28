package agent.trade.models

import io.ktor.server.application.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import java.io.File
import java.util.Base64

// Define a database table to store images
object ImagesTable : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255).uniqueIndex()
    val data = binary("data")
    override val primaryKey = PrimaryKey(id)
}

fun Application.migrateImagesTable() {
    // Migrate the table changes to the database

    SchemaUtils.createMissingTablesAndColumns(ImagesTable)

}

// Insert an image into the database
fun Application.insertImage(file: File, fileName: String) {
    transaction {
        // Read the image file as a byte array
        val imageData = file.readBytes()

        // Insert the image into the database
        ImagesTable.insert {
            it[name] = fileName
            it[data] = imageData
        }
    }
}

fun Application.insertImage(bytes: ByteArray, fileName: String) {
    transaction {
        // Insert the image into the database
        ImagesTable.insert {
            it[name] = fileName
            it[data] = bytes
        }
    }
}

// Retrieve an image from the database
fun Application.getImage(id: Int): ByteArray {
    return transaction {
        // Select the image from the database by ID
        val result = ImagesTable.select { ImagesTable.id eq id }.limit(1).singleOrNull()
            ?: throw NullPointerException("result from getImage returned null")

        // Return the image data as a byte array
        result[ImagesTable.data]
    }
}

fun Application.getImage(name: String): ByteArray {
    return transaction {
        // Select the image from the database by ID
        val result = ImagesTable.select { ImagesTable.name eq name }.limit(1).singleOrNull()
            ?: throw NullPointerException("result from getImage returned null")

        // Return the image data as a byte array
        result[ImagesTable.data]
    }
}

@Serializable
data class ImageResource(
    private val imageName: String,
    private val imageData: String
) {
    fun getImageName(): String {
        return this.imageName
    }

    fun getImageData(): String {
        return this.imageData
    }
}

fun Application.getImages(imageNames: Array<String>): MutableList<ImageResource> {
    return transaction {
        val images = mutableListOf<ImageResource>()

        for (name in imageNames) {
            val imageData = ImagesTable.select { ImagesTable.name eq name }
                .map { it[ImagesTable.data] }
                .singleOrNull()
            if (imageData != null) {
                val base64 = Base64.getEncoder().encodeToString(imageData);
                images.add(ImageResource(name, base64))
            }
        }

        images
    }
}