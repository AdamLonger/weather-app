package com.firethings.something.feature

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firethings.something.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class SplashFragment : Fragment(R.layout.fragment_splash) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(requireContext()) != ConnectionResult.SUCCESS
        ) {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.error_gms_not_available)
                .setNeutralButton(R.string.close_app) { _, _ -> activity?.finishAffinity() }
        } else requestOrHandleLocationPermission()
    }

    private fun requestOrHandleLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    findNavController().navigate(R.id.showMain)
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    Toast.makeText(requireContext(), R.string.warning_fine_location_not_granted, Toast.LENGTH_LONG)
                        .show()
                    findNavController().navigate(SplashFragmentDirections.showMain())
                }
                else -> {
                    AlertDialog.Builder(requireContext())
                        .setMessage(R.string.error_location_not_granted)
                        .setNeutralButton(R.string.close_app) { _, _ -> activity?.finishAffinity() }
                }
            }
        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}
