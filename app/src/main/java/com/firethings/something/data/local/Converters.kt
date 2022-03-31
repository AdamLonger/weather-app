package com.firethings.something.data.local

import androidx.room.TypeConverter
import com.firethings.something.data.api.model.ParameterUnit
import com.firethings.something.data.local.model.Temperature
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(value) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromParameterUnitString(value: String?): ParameterUnit? {
        return value?.let { ParameterUnit.valueOf(it) }
    }

    @TypeConverter
    fun parameterUnitToString(parameter: ParameterUnit?): String? {
        return parameter?.name
    }

    @TypeConverter
    fun fromTemperatureString(value: String?): Temperature? {
        return value?.let { Temperature.fromFullString(it) }
    }

    @TypeConverter
    fun temperatureToString(temperature: Temperature?): String? {
        return temperature?.full
    }
}
