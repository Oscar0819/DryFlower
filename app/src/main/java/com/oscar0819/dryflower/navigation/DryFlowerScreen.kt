package com.oscar0819.dryflower.navigation

import com.oscar0819.core.model.AlbumInfo
import kotlinx.serialization.Serializable

sealed interface DryFlowerScreen {
    @Serializable
    data object Search: DryFlowerScreen

    @Serializable
    data object Album: DryFlowerScreen
}