package com.oscar0819.core.network.model

import com.oscar0819.core.model.AlbumInfo

data class NetworkAlbumResult(
    val resultCount: Int,
    val results: List<NetworkAlbumInfo>
)

data class NetworkAlbumInfo(
    val amgArtistId: Int,
    val artistId: Int,
    val artistName: String,
    val artistViewUrl: String,
    val artworkUrl100: String,
    val artworkUrl60: String,
    val collectionCensoredName: String,
    val collectionExplicitness: String,
    val collectionId: Int,
    val collectionName: String,
    val collectionPrice: Double,
    val collectionType: String,
    val collectionViewUrl: String,
    val contentAdvisoryRating: String,
    val copyright: String,
    val country: String,
    val currency: String,
    val primaryGenreName: String,
    val releaseDate: String,
    val trackCount: Int,
    val wrapperType: String
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

