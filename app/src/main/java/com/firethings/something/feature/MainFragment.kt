package com.firethings.something.feature

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.firethings.something.R
import com.firethings.something.common.setThrottlingOnClickListener
import com.firethings.something.core.BaseFragment
import com.firethings.something.databinding.FragmentMainBinding
import com.firethings.something.feature.adapter.WeatherItem
import com.firethings.something.feature.adapter.WeatherItemDiff
import com.firethings.something.presentation.LatLng
import com.firethings.something.presentation.MainViewModel
import com.firethings.something.presentation.MainViewModel.Action
import com.firethings.something.presentation.MainViewModel.Event
import com.firethings.something.presentation.MainViewModel.State
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<Event, Action, State>(R.layout.fragment_main) {
    override val viewModel: MainViewModel by viewModel()
    private var binding: FragmentMainBinding? = null

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

    private val itemAdapter: ItemAdapter<WeatherItem> = ItemAdapter()
    private val adapter: FastAdapter<WeatherItem> = FastAdapter.Companion.with(itemAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation.let { location ->
                    viewModel.sendEvent(
                        Event.LocationUpdated(
                            LatLng(
                                location.latitude.toFloat(),
                                location.longitude.toFloat()
                            )
                        )
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(requireView())
        binding?.recycler?.adapter = adapter
        binding?.recycler?.addItemDecoration(
            MaterialDividerItemDecoration(requireContext(), MaterialDividerItemDecoration.VERTICAL)
        )

        adapter.onClickListener = { _, _, item, _ ->
            findNavController().navigate(MainFragmentDirections.showDetails(item.data.id))
            true
        }

        binding?.saveFab?.setThrottlingOnClickListener {
            viewModel.sendEvent(Event.LoadWeather)
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onDestroyView() {
        binding?.recycler?.adapter = null
        binding = null
        super.onDestroyView()
    }

    override fun bindState(state: State) {
        binding?.loader?.isVisible = state.isLoading
        binding?.saveFab?.isEnabled = !state.isLoading
        binding?.currentLatLon?.text = state.location.let {
            String.format(getString(R.string.lat_lon_values), it.lat.toString(), it.lon.toString())
        }

        val contents = state.localData.map { WeatherItem(it.weatherEntity) }

        val contentResult = FastAdapterDiffUtil.calculateDiff(itemAdapter, contents, WeatherItemDiff)
        FastAdapterDiffUtil[itemAdapter] = contentResult

        if (state.error != null) {
            Toast.makeText(
                context,
                String.format(getString(R.string.error_value), state.error.localizedMessage), Toast.LENGTH_LONG
            ).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        checkLocationEnabled()
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun checkLocationEnabled() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.error_cant_start_location_updates)
                .setNeutralButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            return
        }
    }

    companion object {
        private const val FUSE_INTERVAL = 100L
        private const val FUSE_FAST_INTERVAL = 50L
        private const val FUSE_MAX_WAIT = 100L
    }
}
