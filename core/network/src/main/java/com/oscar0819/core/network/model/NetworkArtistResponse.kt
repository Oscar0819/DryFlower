package com.oscar0819.core.network.model

import com.oscar0819.core.model.ArtistInfo

data class NetworkArtistResponse(
    val resultCount: Int,
    val results: List<NetworkArtistInfo>
)

data class NetworkArtistInfo(
    val artistId: Int? = null,
    val artistLinkUrl: String? = null,
    val artistName: String? = null,
    val artistType: String? = null,
    val primaryGenreId: Int? = null,
    val primaryGenreName: String? = null,
    val wrapperType: String? = null
)

fun NetworkArtistInfo.asExternalModel(): ArtistInfo =
    ArtistInfo(
        artistId = artistId,
        artistLinkUrl = artistLinkUrl,
        artistName = artistName,
        artistType = artistType,
        primaryGenreId = primaryGenreId,
        primaryGenreName = primaryGenreName,
        wrapperType = wrapperType
    )