package com.firethings.something.provider

import android.net.Uri

object WeatherContract {
    const val COUNT = "count"

    const val AUTHORITY = "com.firethings.something.provider"
    const val CONTENT_PATH = "weather"

    val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$CONTENT_PATH")
    val ROW_COUNT_URI: Uri = Uri.parse("content://$AUTHORITY/$CONTENT_PATH/$COUNT")

    const val SINGLE_RECORD_MIME_TYPE = "com.firethings.something.provider.weather"
    const val MULTIPLE_RECORDS_MIME_TYPE = "com.firethings.something.provider.weatherList"

    object WeatherTable {
        object Columns {
            const val KEY_WEATHER_ID: String = "id"
            const val KEY_WEATHER_LAT: String = "lat"
            const val KEY_WEATHER_LON: String = "lon"
            const val KEY_WEATHER_DATE: String = "date"
            const val KEY_WEATHER_TEMP: String = "temp"
            const val KEY_WEATHER_UNIT: String = "parameterUnit"
        }
    }
}
