package com.firethings.something.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job

abstract class BaseFragment<E : Any, A : Any, S : Any>(contentLayoutId: Int) : Fragment(contentLayoutId) {
    abstract val viewModel: BaseViewModel<E, A, *, S>

    private var stateJob: Job? = null

    override fun onStop() {
        stateJob?.cancel()
        stateJob = null
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        stateJob = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.stateFlow.collect {
                bindState(it)
            }
        }
    }

    abstract fun bindState(state: S)
}
