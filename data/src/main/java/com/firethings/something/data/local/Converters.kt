package com.firethings.something.data.local

import androidx.room.TypeConverter
import com.firethings.something.domain.model.ParameterUnit
import com.firethings.something.domain.model.Temperature
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
        return value?.let { ParameterUnit.fromParameter(it) }
    }

    @TypeConverter
    fun parameterUnitToString(unit: ParameterUnit?): String? {
        return unit?.parameter
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
