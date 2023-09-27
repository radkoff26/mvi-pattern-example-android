package com.github.radkoff26.mvi.user

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.github.radkoff26.mvi.action.Action
import com.github.radkoff26.mvi.action.UserAction
import com.github.radkoff26.mvi.data.Location
import com.github.radkoff26.mvi.store.Dispatcher
import com.github.radkoff26.mvi.store.Middleware
import com.google.android.gms.location.LocationServices

class UserMiddleware(private val context: Context) : Middleware<UserState, Action> {
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    override suspend fun execute(state: UserState, action: Action, dispatcher: Dispatcher<Action>) {
        if (action is UserAction.FetchLocation) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                dispatcher.dispatch(
                    UserAction.LocationUpdate(
                        Location(
                            it.latitude,
                            it.longitude
                        )
                    )
                )
            }
        }
    }
}