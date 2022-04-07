package com.firethings.something.domain.model

sealed class ParameterUnit(val parameter: String) {
    object Default : ParameterUnit(DEFAULT_PARAM)
    object Metric : ParameterUnit(METRIC_PARAM)
    object Imperial : ParameterUnit(IMPERIAL_PARAM)

    companion object {
        private const val DEFAULT_PARAM = "standard"
        private const val METRIC_PARAM = "metric"
        private const val IMPERIAL_PARAM = "standard"

        fun fromParameter(parameter: String) = when (parameter) {
            METRIC_PARAM -> Metric
            IMPERIAL_PARAM -> Imperial
            else -> Default
        }
    }
}
