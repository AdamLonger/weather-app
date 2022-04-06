package com.firethings.something.data.api.model

import com.google.gson.annotations.SerializedName

data class RainModel(
    @SerializedName("1h")
    val oneHour: Float? = null,
    @SerializedName("3h")
    val threeHour: Float? = null,
)
