package com.oscar0819.core.data.repo

import com.oscar0819.core.android.AppCoroutineDispatchers
import com.oscar0819.core.android.logger
import com.oscar0819.core.model.AlbumInfo
import com.oscar0819.core.network.ItunesNetworkDataSource
import com.oscar0819.core.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class AlbumsRepositoryImpl @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val network: ItunesNetworkDataSource
): AlbumsRepository {
    override fun searchAlbum(term: String): Flow<List<AlbumInfo>> =
        flow {
            val albumInfo = network.searchAlbum(term)
            logger(albumInfo)

            val result = albumInfo.results.map {
                it.asExternalModel()
            }

            emit(result)
        }.flowOn(dispatchers.io)
}