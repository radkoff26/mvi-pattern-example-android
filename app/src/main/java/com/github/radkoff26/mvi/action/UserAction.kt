package com.github.radkoff26.mvi.action

import com.github.radkoff26.mvi.data.Location

object UserAction {

    data class LocationUpdate(val location: Location): Action()

    data class NicknameChange(val nickname: String): Action()

    object RandomizeId: Action()

    object FetchLocation: Action()
}
