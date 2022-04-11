package com.firethings.something.common.core

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Job

abstract class MVIFragment<VB : ViewBinding, E : Any, A : Any, S : Any>(
    contentLayoutId: Int,
    viewBinding: (View) -> VB
) : BindingFragment<VB>(contentLayoutId, viewBinding) {
    abstract val viewModel: BaseViewModel<E, A, *, S>

    private var stateJob: Job? = null

    override fun onStart() {
        super.onStart()
        stateJob = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.stateFlow.collect {
                bindState(it)
            }
        }
        viewModel.onStart()
    }

    override fun onStop() {
        stateJob?.cancel()
        stateJob = null
        super.onStop()
    }

    abstract fun bindState(state: S)
}
