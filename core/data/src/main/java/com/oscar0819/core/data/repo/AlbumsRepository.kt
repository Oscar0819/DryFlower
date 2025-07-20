package com.oscar0819.core.data.repo

import androidx.annotation.WorkerThread
import com.oscar0819.core.model.AlbumInfo
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {

    @WorkerThread
    fun searchAlbum(term: String): Flow<List<AlbumInfo>>
}