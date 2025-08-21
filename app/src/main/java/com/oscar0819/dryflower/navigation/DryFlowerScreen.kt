package com.oscar0819.dryflower.navigation

import kotlinx.serialization.Serializable

sealed interface DryFlowerScreen {
    @Serializable
    data object Search: DryFlowerScreen

    @Serializable
    data object SearchDetail: DryFlowerScreen

    @Serializable
    data class Album(val term: String): DryFlowerScreen

    @Serializable
    data class Artist(val artistId: Int): DryFlowerScreen

    @Serializable
    data class AlbumDetail(val collectionId: Int): DryFlowerScreen

}