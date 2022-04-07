package com.firethings.something.provider

import android.database.Cursor
import androidx.core.database.getFloatOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.firethings.something.common.getColumnIndexOrNull
import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.model.ParameterUnit
import com.firethings.something.domain.model.Temperature
import com.firethings.something.provider.WeatherContract.WeatherTable.Columns
import java.util.Date

data class WeatherInfo(
    val id: Int,
    val coordinates: Coordinates,
    val temperature: Temperature,
    val date: Date,
    val parameterUnit: ParameterUnit
) {
    companion object {
        fun fromCursor(cursor: Cursor) = WeatherInfo(
            id = cursor.getColumnIndexOrNull(Columns.KEY_WEATHER_ID) { cursor.getIntOrNull(it) } ?: 0,
            coordinates = Coordinates(
                lat = cursor.getColumnIndexOrNull(Columns.KEY_WEATHER_LAT) { cursor.getFloatOrNull(it) } ?: 0f,
                lon = cursor.getColumnIndexOrNull(Columns.KEY_WEATHER_LON) { cursor.getFloatOrNull(it) } ?: 0f,
            ),
            temperature = requireNotNull(cursor.getColumnIndexOrNull(Columns.KEY_WEATHER_TEMP) {
                cursor.getStringOrNull(
                    it
                )
            }
                ?.let { Temperature.fromFullString(it) }),
            date = requireNotNull(cursor.getColumnIndexOrNull(Columns.KEY_WEATHER_DATE) { cursor.getLongOrNull(it) }
                ?.let { Date(it) }),
            parameterUnit = requireNotNull(cursor.getColumnIndexOrNull(Columns.KEY_WEATHER_UNIT) {
                cursor.getStringOrNull(it)
            }?.let { ParameterUnit.fromParameter(it) }),
        )
    }
}
