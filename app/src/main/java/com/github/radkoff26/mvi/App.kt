package com.github.radkoff26.mvi

import android.app.Application
import com.github.radkoff26.mvi.data.Location
import com.github.radkoff26.mvi.user.UserMiddleware
import com.github.radkoff26.mvi.user.UserReducer
import com.github.radkoff26.mvi.user.UserState
import com.github.radkoff26.mvi.user.UserStore

class App: Application() {
    val userStore: UserStore by lazy {
        UserStore(
            state = UserState(
                0,
                "",
                Location(0.0, 0.0)
            ),
            reducers = listOf(
                UserReducer()
            ),
            middleware = listOf(
                UserMiddleware(this)
            )
        )
    }
}
