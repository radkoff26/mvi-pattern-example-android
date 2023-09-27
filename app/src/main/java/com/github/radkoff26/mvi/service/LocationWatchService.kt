package com.github.radkoff26.mvi.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.github.radkoff26.mvi.action.UserAction
import com.github.radkoff26.mvi.extensions.getStore
import kotlinx.coroutines.*

class LocationWatchService: Service() {
    private val serviceScope = CoroutineScope(Dispatchers.Default + Job())

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        updateLocation()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun updateLocation() {
        serviceScope.launch {
            getStore().dispatch(UserAction.FetchLocation)
            delay(TIMEOUT)
            updateLocation()
        }
    }

    companion object {
        private const val TIMEOUT = 5000L
    }
}