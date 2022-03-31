package com.firethings.something.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.Date

@Entity
data class SysEntity(
    val type: Int? = null,
    @ColumnInfo(name = "sys_id")
    val id: Int? = null,
    @ColumnInfo(name = "sys_message")
    val message: Float? = null,
    val country: String? = null,
    val sunrise: Date? = null,
    val sunset: Date? = null,
)
