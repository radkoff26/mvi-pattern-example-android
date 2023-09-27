package com.github.radkoff26.mvi.store

import com.github.radkoff26.mvi.action.Action

fun interface Reducer<S : State, A: Action> {
    fun reduce(action: A, state: S): S
}