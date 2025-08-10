package com.oscar0819.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistInfo(
    val artistId: Int? = null,
    val artistLinkUrl: String? = null,
    val artistName: String? = null,
    val artistType: String? = null,
    val primaryGenreId: Int? = null,
    val primaryGenreName: String? = null,
    val wrapperType: String? = null
)
