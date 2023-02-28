package agent.trade.dto

import kotlinx.serialization.Serializable


@Serializable
data class ImageResourceRequestDTO(
    private val imageName: String
) {
    fun getImageName(): String {
        return this.imageName
    }
}

@Serializable
data class BatchImageResourceRequestDTO(
    private val imageNames: Array<String>
) {
    fun getImageNames(): Array<String> {
        return this.imageNames;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BatchImageResourceRequestDTO

        if (!imageNames.contentEquals(other.imageNames)) return false

        return true
    }

    override fun hashCode(): Int {
        return imageNames.contentHashCode()
    }
}