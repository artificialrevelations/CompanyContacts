package com.alexzh.company.contacts.common

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class Logger(
    private val lifecycle: Lifecycle,
    private val tag: String
) : LifecycleObserver {

    companion object {
        const val LOGGER_TAG = "App-Logger"
    }

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.d(LOGGER_TAG, "$tag#onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.d(LOGGER_TAG, "$tag#onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.d(LOGGER_TAG, "$tag#onDestroy")
        lifecycle.removeObserver(this)
    }
}