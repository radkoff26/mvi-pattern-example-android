package com.github.radkoff26.mvi.store

import com.github.radkoff26.mvi.action.Action

fun interface Middleware<S : State, A : Action> {
    suspend fun execute(state: S, action: A, dispatcher: Dispatcher<A>)
}