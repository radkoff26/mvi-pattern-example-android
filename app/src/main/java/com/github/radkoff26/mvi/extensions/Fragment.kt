package com.github.radkoff26.mvi.extensions

import androidx.fragment.app.Fragment
import com.github.radkoff26.mvi.user.UserStore

fun Fragment.getStore(): UserStore = requireActivity().application.app.userStore