package com.firethings.something.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.firethings.something.data.local.LocalWeatherStorage
import com.firethings.something.data.local.SimpleWeatherDao
import com.firethings.something.data.local.model.MainEntry
import com.firethings.something.data.local.model.WeatherEntry
import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.model.ParameterUnit
import com.firethings.something.domain.model.Temperature
import com.firethings.something.provider.WeatherContract.AUTHORITY
import com.firethings.something.provider.WeatherContract.CONTENT_PATH
import com.firethings.something.provider.WeatherContract.COUNT
import com.firethings.something.provider.WeatherContract.MULTIPLE_RECORDS_MIME_TYPE
import com.firethings.something.provider.WeatherContract.SINGLE_RECORD_MIME_TYPE
import com.firethings.something.provider.WeatherContract.WeatherTable.Columns
import java.lang.Long.parseLong
import java.util.Date

class WeatherContentProvider : ContentProvider() {
    private lateinit var storage: LocalWeatherStorage
    private lateinit var dao: SimpleWeatherDao
    private lateinit var uriMatcher: UriMatcher

    override fun onCreate(): Boolean {
        context?.let {
            storage = LocalWeatherStorage.build(it)
            dao = storage.simpleWeatherDao()
            initializeUriMatching()
        }
        return true
    }

    private fun initializeUriMatching() {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, CONTENT_PATH, URI_ALL_ITEMS_CODE)
            addURI(AUTHORITY, "$CONTENT_PATH/#", URI_ONE_ITEM_CODE)
            addURI(AUTHORITY, "$CONTENT_PATH/$COUNT", URI_COUNT_CODE)
        }
    }

    override fun getType(uri: Uri): String? = when (uriMatcher.match(uri)) {
        URI_ALL_ITEMS_CODE -> MULTIPLE_RECORDS_MIME_TYPE
        URI_ONE_ITEM_CODE -> SINGLE_RECORD_MIME_TYPE
        else -> null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return selectionArgs?.get(0)?.let {
            return dao.deleteWeatherById(parseLong(it))
        } ?: 0
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        values?.let { content ->
            val lat = content.getAsFloat(Columns.KEY_WEATHER_LAT)
            val lon = content.getAsFloat(Columns.KEY_WEATHER_LON)
            val dateLong = content.getAsLong(Columns.KEY_WEATHER_DATE)
            val tempString = content.getAsString(Columns.KEY_WEATHER_TEMP)
            val unitString = content.getAsString(Columns.KEY_WEATHER_UNIT)

            dao.insertEntry(
                WeatherEntry(
                    coordinates = Coordinates(lat, lon),
                    parameterUnit = ParameterUnit.fromParameter(unitString),
                    date = Date(dateLong),
                    main = MainEntry(
                        temp = Temperature.fromFullString(tempString)
                    )
                )
            )
        }
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        var cursor: Cursor? = null
        when (uriMatcher.match(uri)) {
            URI_ALL_ITEMS_CODE -> cursor = dao.weatherCursor()
            URI_ONE_ITEM_CODE -> uri.lastPathSegment?.let {
                cursor = dao.weatherCursorById(parseLong(it))
            }
            URI_COUNT_CODE -> cursor = dao.weatherCountCursor()
            UriMatcher.NO_MATCH -> throw IllegalArgumentException("Unknown URI: $uri")
            else -> throw IllegalArgumentException("Unexpected Problem On URI: $uri")
        }
        return cursor
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        values?.let { content ->
            val id = content.getAsLong(Columns.KEY_WEATHER_ID)
            val lat = content.getAsFloat(Columns.KEY_WEATHER_LAT)
            val lon = content.getAsFloat(Columns.KEY_WEATHER_LON)
            val dateLong = content.getAsLong(Columns.KEY_WEATHER_DATE)
            val tempString = content.getAsString(Columns.KEY_WEATHER_TEMP)
            val unitString = content.getAsString(Columns.KEY_WEATHER_UNIT)

            dao.updateEntry(
                WeatherEntry(
                    id = id,
                    coordinates = Coordinates(lat, lon),
                    parameterUnit = ParameterUnit.fromParameter(unitString),
                    date = Date(dateLong),
                    main = MainEntry(
                        temp = Temperature.fromFullString(tempString)
                    )
                )
            )
        }
        return 0
    }

    companion object {
        private const val URI_ALL_ITEMS_CODE = 10
        private const val URI_ONE_ITEM_CODE = 20
        private const val URI_COUNT_CODE = 30
    }
}
