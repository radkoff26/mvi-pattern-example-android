package com.github.radkoff26.mvi.extensions

import android.app.Service
import com.github.radkoff26.mvi.user.UserStore

fun Service.getStore(): UserStore = application.app.userStore
