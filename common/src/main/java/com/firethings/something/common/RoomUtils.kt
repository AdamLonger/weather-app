package com.firethings.something.common

import android.database.Cursor

fun <T> Cursor.getColumnIndexOrNull(column: String, onTake: (Int) -> T) =
    getColumnIndex(column).takeIf { it >= 0 }?.let { onTake(it) }
