package com.github.radkoff26.mvi.user

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.github.radkoff26.mvi.action.Action
import com.github.radkoff26.mvi.store.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserStore(
    private var state: UserState,
    private val reducers: List<Reducer<UserState, Action>>,
    private val middleware: List<Middleware<UserState, Action>>
) : DefaultLifecycleObserver, Store<UserState>, Dispatcher<Action> {
    private val storeScope = CoroutineScope(Dispatchers.Default)
    private val subscribers: MutableMap<Lifecycle, StateCallback<UserState>> = HashMap()

    override fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
        subscribers.remove(owner.lifecycle)
    }

    @Synchronized
    override fun getCurrentState(): UserState = state

    override fun dispatch(action: Action) {
        storeScope.launch(Dispatchers.Main) {
            var newState: UserState = state.copy()
            reducers.forEach {
                newState = it.reduce(action, state)
            }
            if (newState != state) {
                synchronized(this) {
                    state = newState
                }
                subscribers.values.forEach {
                    it.onStateChanged(newState)
                }
            }
        }
        storeScope.launch {
            middleware.forEach {
                it.execute(state, action, this@UserStore)
            }
        }
    }

    fun subscribe(owner: LifecycleOwner, callback: StateCallback<UserState>) {
        owner.lifecycle.addObserver(this)
        subscribers[owner.lifecycle] = callback
    }
}