package com.firethings.something.common

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

object Formatters {
    val oneDecimalPlaces = DecimalFormat("#.#")
    val dateTimeFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale("hu", "HU"))
}
