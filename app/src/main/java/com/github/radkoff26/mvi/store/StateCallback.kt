package com.github.radkoff26.mvi.store

fun interface StateCallback<T : State> {
    fun onStateChanged(value: T)
}
