package com.firethings.something

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.firethings.something.common.LocationClient
import com.firethings.something.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private val locationClient: LocationClient by inject()
    private var destinationListener: NavController.OnDestinationChangedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationClient.initialize(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_content_main)
    }

    override fun onStart() {
        super.onStart()
        destinationListener =
            NavController.OnDestinationChangedListener { _, _, _ -> hideSoftKeyboard(this@MainActivity) }.also {
                navController?.addOnDestinationChangedListener(it)
            }
    }

    override fun onStop() {
        destinationListener?.let { navController?.removeOnDestinationChangedListener(it) }
        super.onStop()
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager? =
            activity.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        if (inputMethodManager?.isAcceptingText == true) {
            activity.currentFocus?.windowToken?.let { token ->
                inputMethodManager.hideSoftInputFromWindow(token, 0)
            }
        }
    }
}
