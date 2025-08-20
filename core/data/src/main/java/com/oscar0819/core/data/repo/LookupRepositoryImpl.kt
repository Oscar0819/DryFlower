package com.oscar0819.core.data.repo

import com.oscar0819.core.android.CoroutineDispatchers
import com.oscar0819.core.model.AlbumInfo
import com.oscar0819.core.model.AlbumTrackInfo
import com.oscar0819.core.model.asExternalModel
import com.oscar0819.core.network.ItunesNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LookupRepositoryImpl @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val network: ItunesNetworkDataSource,
): LookupRepository {
    override fun lookupAlbumTracks(collectionId: Int): Flow<List<AlbumTrackInfo>> =
        flow {
            val albumTrackInfo = network.lookupAlbumTracks(collectionId)

            val result: List<AlbumTrackInfo> = albumTrackInfo.results.mapIndexed { index, item ->
                if (index == 0) {
                    changeUrl(item.asExternalModel())
                } else {
                    item.asExternalModel()
                }
            }

            emit(result)
        }.flowOn(dispatchers.io)

    fun changeUrl(albumTrackInfo: AlbumTrackInfo): AlbumTrackInfo {
        val artworkUrl1200 = albumTrackInfo.artworkUrl100?.replace(RESOLUTION_100, RESOLUTION_1200)
        return albumTrackInfo.copy(artworkUrl1200 = artworkUrl1200)
    }

    companion object {
        const val RESOLUTION_100 = "100x100"
        const val RESOLUTION_1200 = "1200x1200"
    }
}