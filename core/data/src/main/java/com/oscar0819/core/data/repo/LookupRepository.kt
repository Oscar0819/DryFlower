package com.oscar0819.core.data.repo

import androidx.annotation.WorkerThread
import com.oscar0819.core.model.AlbumTrackInfo
import kotlinx.coroutines.flow.Flow

interface LookupRepository {
    @WorkerThread
    fun lookupAlbumTracks(collectionId: Int): Flow<List<AlbumTrackInfo>>
}