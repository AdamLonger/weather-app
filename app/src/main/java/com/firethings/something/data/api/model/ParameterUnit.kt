package com.firethings.something.data.api.model

enum class ParameterUnit(val parameter: String?) {
    DEFAULT(null),
    METRIC("metric"),
    IMPERIAL("imperial"),
}
