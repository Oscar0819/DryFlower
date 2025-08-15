package com.oscar0819.core.data.repo

import com.oscar0819.core.model.AlbumInfo
import com.oscar0819.core.model.ArtistInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSearchRepository : SearchRepository {
    override fun searchAlbum(term: String): Flow<List<AlbumInfo>> = flowOf()

    override fun searchArtist(term: String): Flow<List<ArtistInfo>> = flowOf()
}