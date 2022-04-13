package com.firethings.something

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Looper
import com.firethings.something.common.LocationClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class DefaultLocationClient : LocationClient {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private val locationRequest by lazy {
        LocationRequest.create().apply {
            interval = FUSE_INTERVAL
            fastestInterval = FUSE_FAST_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = FUSE_MAX_WAIT
        }
    }

    override fun setOnLocationCallback(onLocation: (location: Location) -> Unit) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                onLocation(locationResult.lastLocation)
            }
        }
    }

    override fun initialize(activity: Activity) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    @SuppressLint("MissingPermission")
    override fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val FUSE_INTERVAL = 100L
        private const val FUSE_FAST_INTERVAL = 50L
        private const val FUSE_MAX_WAIT = 3000L
    }
}
