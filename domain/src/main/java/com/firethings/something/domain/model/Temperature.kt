package com.firethings.something.domain.model

import com.firethings.something.common.Formatters

sealed class Temperature {
    abstract val value: Float
    abstract val suffix: String

    val formatted: String
        get() = Formatters.oneDecimalPlaces.format(value) + " " + suffix

    val full: String
        get() = "$value $suffix"

    data class Kelvin(override val value: Float) : Temperature() {
        override val suffix: String = KELVIN_SUFFIX
    }

    data class Celsius(override val value: Float) : Temperature() {
        override val suffix: String = CELSIUS_SUFFIX
    }

    data class Fahrenheit(override val value: Float) : Temperature() {
        override val suffix: String = FAHRENHEIT_SUFFIX
    }

    companion object {
        private const val KELVIN_SUFFIX = "°K"
        private const val CELSIUS_SUFFIX = "°C"
        private const val FAHRENHEIT_SUFFIX = "°F"

        @Suppress("MagicNumber")
        fun fromFullString(string: String): Temperature {
            val part = string.dropLast(3).toFloat()
            return when (string.takeLast(2)) {
                CELSIUS_SUFFIX -> Celsius(part)
                FAHRENHEIT_SUFFIX -> Fahrenheit(part)
                else -> Fahrenheit(part)
            }
        }

        fun fromValueAndUnit(value: Float, unit: ParameterUnit) = when (unit) {
            ParameterUnit.DEFAULT -> Kelvin(value)
            ParameterUnit.METRIC -> Celsius(value)
            ParameterUnit.IMPERIAL -> Fahrenheit(value)
        }
    }
}
