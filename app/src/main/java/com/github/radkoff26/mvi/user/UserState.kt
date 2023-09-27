package com.github.radkoff26.mvi.user

import com.github.radkoff26.mvi.data.Location
import com.github.radkoff26.mvi.store.State

data class UserState(
    val id: Long,
    val nickname: String,
    val location: Location
): State
