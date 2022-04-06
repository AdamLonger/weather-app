package com.firethings.something.common.core

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BindingFragment<VB : ViewBinding>(
    contentLayoutId: Int,
    private val bind: (View) -> VB
) : Fragment(contentLayoutId) {
    private var _binding: VB? = null
    protected val binding
        get() = requireNotNull(_binding) {
            "Binding should only be called between onViewCreated and onViewDestroyed!"
        }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = bind(view)
    }

    @CallSuper
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
