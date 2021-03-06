package com.firethings.something.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.firethings.something.common.Formatters
import com.firethings.something.common.Formatters.fourDecimalPlaces
import com.firethings.something.common.LocationClient
import com.firethings.something.common.core.Failed
import com.firethings.something.common.core.MVIFragment
import com.firethings.something.common.setThrottlingOnClickListener
import com.firethings.something.domain.model.Coordinates
import com.firethings.something.presentation.MainViewModel
import com.firethings.something.presentation.MainViewModel.Action
import com.firethings.something.presentation.MainViewModel.Event
import com.firethings.something.presentation.MainViewModel.State
import com.firethings.something.weather.adapter.WeatherItem
import com.firethings.something.weather.adapter.WeatherItemDiff
import com.firethings.something.weather.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : MVIFragment<FragmentMainBinding, Event, Action, State>(
    R.layout.fragment_main,
    FragmentMainBinding::bind
) {
    override val viewModel: MainViewModel by viewModel()
    private val locationClient: LocationClient by inject()

    private val itemAdapter: ItemAdapter<GenericItem> = ItemAdapter()
    private val adapter: FastAdapter<GenericItem> = FastAdapter.with(itemAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationClient.setOnLocationCallback { location ->
            viewModel.sendEvent(
                Event.LocationUpdated(
                    Coordinates(
                        location.latitude.toFloat(),
                        location.longitude.toFloat()
                    )
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter
        binding.recycler.addItemDecoration(
            MaterialDividerItemDecoration(requireContext(), MaterialDividerItemDecoration.VERTICAL)
        )

        adapter.onClickListener = { _, _, item, _ ->
            when (item) {
                is WeatherItem -> item.data.localId.let {
                    findNavController().navigate(MainFragmentDirections.showDetails(it))
                }
            }
            true
        }

        binding.saveFab.setThrottlingOnClickListener {
            viewModel.sendEvent(Event.SaveWeather)
        }

        binding.headerLayout.refreshTapArea.setThrottlingOnClickListener {
            viewModel.sendEvent(
                if (viewModel.state.periodicEnabled) {
                    Event.StopPeriodicUpdates
                } else {
                    Event.StartPeriodicUpdates
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        checkLocationEnabled(requireContext()) {
            locationClient.startLocationUpdates()
        }
    }

    override fun onPause() {
        locationClient.stopLocationUpdates()
        super.onPause()
    }

    override fun onDestroyView() {
        binding.recycler.adapter = null
        super.onDestroyView()
    }

    override fun bindState(state: State) {
        binding.loader.isVisible = state.isLoading
        binding.saveFab.isEnabled = !state.isLoading
        binding.headerLayout.refreshSwitch.isChecked = state.periodicEnabled
        binding.headerLayout.latestLatLon.text = state.location.let {
            String.format(
                getString(R.string.lat_lon_values),
                fourDecimalPlaces.format(it.lat), fourDecimalPlaces.format(it.lon)
            )
        }

        val unknownText = binding.root.context.getString(R.string.unknown)
        val refreshing = state.refreshingData.getOrNull()
        binding.refreshingLayout.root.isVisible = refreshing != null
        binding.refreshingLayout.temperature.text = refreshing?.main?.temp?.formatted ?: unknownText
        binding.refreshingLayout.date.text =
            refreshing?.date?.let { Formatters.dateTimeFormat.format(it) } ?: unknownText
        binding.refreshingLayout.coordinates.text =
            refreshing?.coordinates?.let { coords ->
                fourDecimalPlaces.format(coords.lat) + " : " + fourDecimalPlaces.format(coords.lon)
            } ?: unknownText

        val contents = state.localData.getOrNull()?.map { WeatherItem(it) } ?: emptyList()

        val contentResult = FastAdapterDiffUtil.calculateDiff(itemAdapter, contents, WeatherItemDiff)
        FastAdapterDiffUtil[itemAdapter] = contentResult

        (state.localData as? Failed<*, Throwable>)?.let { failed ->
            Toast.makeText(
                context,
                String.format(getString(R.string.error_value), failed.error.localizedMessage), Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun checkLocationEnabled(context: Context, onSuccess: () -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            AlertDialog.Builder(context)
                .setMessage("Cant start location updates!")
                .setNeutralButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                .show()
            return
        }
        onSuccess()
    }
}
