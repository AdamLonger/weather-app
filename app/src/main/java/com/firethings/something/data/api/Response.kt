package com.firethings.something.data.api

data class Response(
    val statusCode: Int,
    val content: ByteArray,
    val timestamp: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Response

        if (statusCode != other.statusCode) return false
        if (!content.contentEquals(other.content)) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = statusCode
        result = 31 * result + 43 * timestamp.toInt() + content.contentHashCode()
        return result
    }
}
