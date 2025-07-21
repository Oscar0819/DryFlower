package com.oscar0819.core.data.repo

import com.oscar0819.core.model.AlbumInfo
import com.oscar0819.core.network.ItunesNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


internal class AlbumsRepositoryImpl @Inject constructor(
    private val network: ItunesNetworkDataSource
): AlbumsRepository {
    override fun searchAlbum(term: String): Flow<List<AlbumInfo>> =
        flow {
            val albumInfo = network.searchAlbum(term)
        }
}