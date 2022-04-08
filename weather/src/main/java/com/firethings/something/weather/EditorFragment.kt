package com.firethings.something.weather

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.firethings.something.common.Formatters
import com.firethings.something.common.core.MVIFragment
import com.firethings.something.common.setThrottlingOnClickListener
import com.firethings.something.presentation.EditorViewModel
import com.firethings.something.presentation.EditorViewModel.Action
import com.firethings.something.presentation.EditorViewModel.Event
import com.firethings.something.presentation.EditorViewModel.State
import com.firethings.something.weather.databinding.FragmentEditorBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditorFragment : MVIFragment<FragmentEditorBinding, Event, Action, State>(
    R.layout.fragment_editor,
    FragmentEditorBinding::bind
) {
    override val viewModel: EditorViewModel by viewModel()
    private val args: DetailsFragmentArgs by navArgs()

    private var latWatcher: TextWatcher? = null
    private var lonWatcher: TextWatcher? = null
    private var tempWatcher: TextWatcher? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendEvent(Event.LoadWeatherData(args.weatherId))

        binding.backBtn.setThrottlingOnClickListener {
            findNavController().popBackStack()
        }

        binding.saveBtn.setThrottlingOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.update_item_confirmation)
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    viewModel.sendEvent(Event.SaveUpdates)
                    dialog.dismiss()
                }.show()
        }
    }

    override fun onResume() {
        super.onResume()
        applyTextWatchers()
    }

    override fun bindState(state: State) {
        binding.loader.isVisible = state.isLoading

        val weather = state.weather.getOrNull()
        if (weather != null && !binding.latLayout.isEnabled) {
            binding.latLayout.isEnabled = true
            binding.lonLayout.isEnabled = true
            binding.tempLayout.isEnabled = true

            binding.latInput.setText(Formatters.fourDecimalPlaces.format(weather.coordinates.lat))
            binding.lonInput.setText(Formatters.fourDecimalPlaces.format(weather.coordinates.lon))
            binding.tempInput.setText(Formatters.fourDecimalPlaces.format(weather.main?.temp?.value ?: 0))
            binding.tempLayout.suffixText = weather.main?.temp?.suffix ?: ""
        }

        if (state.saved) {
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        removeTextWatchers()
        super.onPause()
    }

    private fun removeTextWatchers() {
        tempWatcher?.let { binding.tempInput.removeTextChangedListener(it) }
        latWatcher?.let { binding.latInput.removeTextChangedListener(it) }
        lonWatcher?.let { binding.lonInput.removeTextChangedListener(it) }
        tempWatcher = null
        latWatcher = null
        lonWatcher = null
    }

    private fun applyTextWatchers() {
        tempWatcher = binding.tempInput
            .addChangeListenerWithEvent(binding.tempLayout) { viewModel.sendEvent(Event.SetTemp(it)) }
        latWatcher = binding.latInput
            .addChangeListenerWithEvent(binding.latLayout) { viewModel.sendEvent(Event.SetLat(it)) }
        lonWatcher = binding.lonInput
            .addChangeListenerWithEvent(binding.lonLayout) { viewModel.sendEvent(Event.SetLon(it)) }
    }

    private fun TextInputEditText.addChangeListenerWithEvent(
        parent: TextInputLayout,
        onChanged: (Float?) -> Unit
    ): TextWatcher {
        return addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                val data = text?.toString()?.toFloatOrNull()
                onChanged(data)
                parent.error = if (data != null) null else getString(R.string.error_invalid_input)
            }
        )
    }
}
