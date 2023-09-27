package com.github.radkoff26.mvi.store

interface Store<S : State> {
    fun getCurrentState(): S
}