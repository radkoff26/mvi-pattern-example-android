package com.github.radkoff26.mvi.extensions

import android.app.Application
import com.github.radkoff26.mvi.App

val Application.app: App
    get() = this as App