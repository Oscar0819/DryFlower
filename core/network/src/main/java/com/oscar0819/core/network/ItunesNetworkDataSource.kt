package com.oscar0819.core.network

import com.oscar0819.core.network.model.AlbumResult

interface ItunesNetworkDataSource {
    suspend fun searchAlbum(
        searchTerm: String,
        entity: String = "album",
        country: String = "US"): AlbumResult
}