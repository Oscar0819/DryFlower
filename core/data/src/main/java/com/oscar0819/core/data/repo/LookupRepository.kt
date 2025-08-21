package com.oscar0819.core.data.repo

import androidx.annotation.WorkerThread
import com.oscar0819.core.model.AlbumTrackInfo
import com.oscar0819.core.model.ArtistAlbumInfo
import com.oscar0819.core.model.ArtistSongInfo
import kotlinx.coroutines.flow.Flow

interface LookupRepository {
    @WorkerThread
    fun lookupAlbumTracks(collectionId: Int): Flow<List<AlbumTrackInfo>>

    @WorkerThread
    fun lookupArtistAlbums(artistId: Int): Flow<List<ArtistAlbumInfo>>

    @WorkerThread
    fun lookupArtistSongs(artistId: Int): Flow<List<ArtistSongInfo>>

}