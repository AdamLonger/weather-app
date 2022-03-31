package com.firethings.something.common

import android.view.View

fun View.setThrottlingOnClickListener(onClicked: () -> Unit) {
    setOnClickListener { view ->
        val currentMillis = System.currentTimeMillis()
        val lastMillis = view.getTag(CLICK_TAG) as? Long

        if (lastMillis == null || currentMillis - lastMillis> CLICK_THROTTLE_DURATION) {
            view.tag = CLICK_TAG
            onClicked()
        }
    }
}

private const val CLICK_TAG = 8008
private const val CLICK_THROTTLE_DURATION = 500
