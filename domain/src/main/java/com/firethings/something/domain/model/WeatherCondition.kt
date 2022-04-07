package com.firethings.something.domain.model

sealed class WeatherCondition {
    abstract val apiId: Int?
    abstract val main: String?
    abstract val description: String?
    abstract val icon: String?

    class Stored(
        val localId: Long,
        val localParentId: Long,
        override val apiId: Int? = null,
        override val main: String? = null,
        override val description: String? = null,
        override val icon: String? = null
    ) : WeatherCondition()

    class Simple(
        override val apiId: Int? = null,
        override val main: String? = null,
        override val description: String? = null,
        override val icon: String? = null
    ) : WeatherCondition()
}
