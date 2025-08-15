package com.oscar0819.core.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkAlbumResponse(
    val resultCount: Int,
    val results: List<NetworkAlbumInfo>
)

@Serializable
data class NetworkAlbumInfo(
    val amgArtistId: Int? = null,
    val artistId: Int? = null,
    val artistName: String? = null,
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
    val contentAdvisoryRating: String? = null,
    val copyright: String? = null,
    val country: String? = null,
    val currency: String? = null,
    val primaryGenreName: String? = null,
    val releaseDate: String? = null,
    val trackCount: Int? = null,
    val wrapperType: String? = null
)

fun NetworkAlbumInfo.asExternalModel(): AlbumInfo =
    AlbumInfo(
        amgArtistId = amgArtistId,
        artistId = artistId,
        artistName = artistName,
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
        contentAdvisoryRating = contentAdvisoryRating,
        copyright = copyright,
        country = country,
        currency = currency,
        primaryGenreName = primaryGenreName,
        releaseDate = releaseDate,
        trackCount = trackCount,
        wrapperType = wrapperType,
    )

