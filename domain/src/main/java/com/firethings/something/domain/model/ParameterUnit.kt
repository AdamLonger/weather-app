package com.firethings.something.domain.model

enum class ParameterUnit(val parameter: String?) {
    DEFAULT(null),
    METRIC("metric"),
    IMPERIAL("imperial"),
}
