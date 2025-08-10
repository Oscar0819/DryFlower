package com.oscar0819.core.network

import com.oscar0819.core.network.model.NetworkAlbumResponse
import com.oscar0819.core.network.model.NetworkArtistResponse

interface ItunesNetworkDataSource {
    suspend fun searchAlbum(
        searchTerm: String,
        entity: String = "album",
        country: String = "US"): NetworkAlbumResponse

    suspend fun searchArtist(
        searchTerm: String,
        entity: String = "musicArtist",
        country: String = "US"): NetworkArtistResponse
}