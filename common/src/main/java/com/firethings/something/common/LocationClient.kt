package com.firethings.something.common

import android.app.Activity
import android.location.Location

interface LocationClient {
    fun initialize(activity: Activity)
    fun setOnLocationCallback(onLocation: (location: Location) -> Unit)
    fun startLocationUpdates()
    fun stopLocationUpdates()
}
