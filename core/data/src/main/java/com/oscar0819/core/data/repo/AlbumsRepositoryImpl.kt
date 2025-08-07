package com.oscar0819.core.data.repo

import com.oscar0819.core.android.AppCoroutineDispatchers
import com.oscar0819.core.android.logger
import com.oscar0819.core.model.AlbumInfo
import com.oscar0819.core.network.ItunesNetworkDataSource
import com.oscar0819.core.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

internal class AlbumsRepositoryImpl @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val network: ItunesNetworkDataSource
): AlbumsRepository {
    override fun searchAlbum(
        term: String
    ): Flow<List<AlbumInfo>> =
        flow {
            val albumInfo = network.searchAlbum(term)
            logger(albumInfo)

            val result: List<AlbumInfo> = albumInfo.results.map {
                val externalModel = it.asExternalModel()
                changeUrl(externalModel)
            }

            emit(result)
        }.flowOn(dispatchers.io)

    fun changeUrl(albumInfo: AlbumInfo): AlbumInfo {
        val artworkUrl1200 = albumInfo.artworkUrl100?.replace(RESOLUTION_100, RESOLUTION_1200)
        return albumInfo.copy(artworkUrl1200 = artworkUrl1200)
    }

    companion object {
        const val RESOLUTION_100 = "100x100"
        const val RESOLUTION_1200 = "1200x1200"
    }
}