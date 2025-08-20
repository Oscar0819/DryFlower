package com.oscar0819.core.model

import kotlinx.serialization.Serializable

@Serializable
data class AlbumTrackInfo(
    val artistId: Int? = null,
    val artistName: String? = null,
    val artistViewUrl: String? = null,
    val artworkUrl1200: String? = null, // 추가
    val artworkUrl100: String? = null,
    val artworkUrl30: String? = null,
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
    val discCount: Int? = null,
    val discNumber: Int? = null,
    val isStreamable: Boolean? = null,
    val kind: String? = null,
    val previewUrl: String? = null,
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
