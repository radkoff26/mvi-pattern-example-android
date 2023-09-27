package com.github.radkoff26.mvi.user

import com.github.radkoff26.mvi.action.Action
import com.github.radkoff26.mvi.action.UserAction
import com.github.radkoff26.mvi.store.Reducer
import kotlin.random.Random

class UserReducer : Reducer<UserState, Action> {

    override fun reduce(action: Action, state: UserState): UserState =
        when (action) {
            is UserAction.LocationUpdate -> {
                state.copy(location = action.location)
            }
            is UserAction.NicknameChange -> {
                state.copy(nickname = action.nickname)
            }
            is UserAction.RandomizeId -> {
                state.copy(id = Random.nextLong())
            }
            else -> state
        }
}
