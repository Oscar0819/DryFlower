package com.oscar0819.core.network

import com.oscar0819.core.model.NetworkAlbumResponse
import com.oscar0819.core.model.NetworkAlbumTrackResponse
import com.oscar0819.core.model.NetworkArtistResponse

interface ItunesNetworkDataSource {
    suspend fun searchAlbum(
        searchTerm: String,
        entity: String = "album",
        country: String = "US"
    ): NetworkAlbumResponse

    suspend fun searchArtist(
        searchTerm: String,
        entity: String = "musicArtist",
        country: String = "US"
    ): NetworkArtistResponse

    suspend fun lookupAlbumTracks(
        collectionId: Int,
        entity: String = "song"
    ): NetworkAlbumTrackResponse
}