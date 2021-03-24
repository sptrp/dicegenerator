package com.ivan.dicegenerator

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyLiveCycleObserver: LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateEvent() {
        Log.i(LOG_TAG, "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartEvent() {
        Log.i(LOG_TAG, "onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeEvent() {
        Log.i(LOG_TAG, "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseEvent() {
        Log.i(LOG_TAG, "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopEvent() {
        Log.i(LOG_TAG, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyEvent() {
        Log.i(LOG_TAG, "onDestroy")
    }

}