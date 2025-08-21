package com.oscar0819.core.network

import com.oscar0819.core.model.NetworkAlbumResponse
import com.oscar0819.core.model.NetworkAlbumTrackResponse
import com.oscar0819.core.model.NetworkArtistAlbumsResponse
import com.oscar0819.core.model.NetworkArtistResponse
import com.oscar0819.core.model.NetworkArtistSongsResponse

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

    suspend fun lookupArtistAlbums(
        artistId: Int,
        entity: String = "album"
    ): NetworkArtistAlbumsResponse

    suspend fun lookupArtistSongs(
        artistId: Int,
        entity: String = "song"
    ): NetworkArtistSongsResponse
}