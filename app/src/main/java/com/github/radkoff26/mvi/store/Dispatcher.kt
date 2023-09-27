package com.github.radkoff26.mvi.store

import com.github.radkoff26.mvi.action.Action

fun interface Dispatcher<A: Action> {
    fun dispatch(action: A)
}