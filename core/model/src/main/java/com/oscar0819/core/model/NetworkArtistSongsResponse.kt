package com.oscar0819.core.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkArtistSongsResponse(
    val resultCount: Int,
    val results: List<NetworkArtistSongInfo>
)

@Serializable
data class NetworkArtistSongInfo(
    val artistId: Int? = null,
    val artistLinkUrl: String? = null,
    val artistName: String? = null,
    val artistType: String? = null,
    val artistViewUrl: String? = null,
    val artworkUrl100: String? = null,
    val artworkUrl30: String? = null,
    val artworkUrl60: String? = null,
    val collectionCensoredName: String? = null,
    val collectionExplicitness: String? = null,
    val collectionId: Int? = null,
    val collectionName: String? = null,
    val collectionPrice: Double? = null,
    val collectionViewUrl: String? = null,
    val country: String? = null,
    val currency: String? = null,
    val discCount: Int? = null,
    val discNumber: Int? = null,
    val isStreamable: Boolean? = null,
    val kind: String? = null,
    val previewUrl: String? = null,
    val primaryGenreId: Int? = null,
    val primaryGenreName: String? = null,
    val releaseDate: String? = null,
    val trackCensoredName: String? = null,
    val trackCount: Int? = null,
    val trackExplicitness: String? = null,
    val trackId: Int? = null,
    val trackName: String? = null,
    val trackNumber: Int? = null,
    val trackPrice: Double? = null,
    val trackTimeMillis: Int? = null,
    val trackViewUrl: String? = null,
    val wrapperType: String? = null
)

fun NetworkArtistSongInfo.asExternalModel(): ArtistSongInfo =
    ArtistSongInfo(
        artistId = artistId,
        artistLinkUrl = artistLinkUrl,
        artistName = artistName,
        artistType = artistType,
        artistViewUrl = artistViewUrl,
        artworkUrl100 = artworkUrl100,
        artworkUrl30 = artworkUrl30,
        artworkUrl60 = artworkUrl60,
        collectionCensoredName = collectionCensoredName,
        collectionExplicitness = collectionExplicitness,
        collectionId = collectionId,
        collectionName = collectionName,
        collectionPrice = collectionPrice,
        collectionViewUrl = collectionViewUrl,
        country = country,
        currency = currency,
        discCount = discCount,
        discNumber = discNumber,
        isStreamable = isStreamable,
        kind = kind,
        previewUrl = previewUrl,
        primaryGenreId = primaryGenreId,
        primaryGenreName = primaryGenreName,
        releaseDate = releaseDate,
        trackCensoredName = trackCensoredName,
        trackCount = trackCount,
        trackExplicitness = trackExplicitness,
        trackId = trackId,
        trackName = trackName,
        trackNumber = trackNumber,
        trackPrice = trackPrice,
        trackTimeMillis = trackTimeMillis,
        trackViewUrl = trackViewUrl,
        wrapperType = wrapperType
    )
