package com.oscar0819.core.data.repo

import com.oscar0819.core.android.CoroutineDispatchers
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

            val result: List<AlbumTrackInfo> = albumTrackInfo.results.map {
                it.asExternalModel()
            }

            emit(result)
        }.flowOn(dispatchers.io)
}