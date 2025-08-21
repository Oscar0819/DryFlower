package com.oscar0819.core.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkArtistAlbumsResponse(
    val resultCount: Int,
    val results: List<NetworkArtistAlbumInfo>
)

@Serializable
data class NetworkArtistAlbumInfo(
    val artistId: Int? = null,
    val artistLinkUrl: String? = null,
    val artistName: String? = null,
    val artistType: String? = null,
    val artistViewUrl: String? = null,
    val artworkUrl100: String? = null,
    val artworkUrl60: String? = null,
    val collectionCensoredName: String? = null,
    val collectionExplicitness: String? = null,
    val collectionId: Int? = null,
    val collectionName: String? = null,
    val collectionPrice: Double? = null,
    val collectionType: String? = null,
    val collectionViewUrl: String? = null,
    val copyright: String? = null,
    val country: String? = null,
    val currency: String? = null,
    val primaryGenreId: Int? = null,
    val primaryGenreName: String? = null,
    val releaseDate: String? = null,
    val trackCount: Int? = null,
    val wrapperType: String? = null
)

fun NetworkArtistAlbumInfo.asExternalModel(): ArtistAlbumInfo =
    ArtistAlbumInfo(
        artistId = artistId,
        artistLinkUrl = artistLinkUrl,
        artistName = artistName,
        artistType = artistType,
        artistViewUrl = artistViewUrl,
        artworkUrl100 = artworkUrl100,
        artworkUrl60 = artworkUrl60,
        collectionCensoredName = collectionCensoredName,
        collectionExplicitness = collectionExplicitness,
        collectionId = collectionId,
        collectionName = collectionName,
        collectionPrice = collectionPrice,
        collectionType = collectionType,
        collectionViewUrl = collectionViewUrl,
        copyright = copyright,
        country = country,
        currency = currency,
        primaryGenreId = primaryGenreId,
        primaryGenreName = primaryGenreName,
        releaseDate = releaseDate,
        trackCount = trackCount,
        wrapperType = wrapperType
    )