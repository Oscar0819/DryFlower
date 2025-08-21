package com.oscar0819.core.data.repo

import com.oscar0819.core.android.CoroutineDispatchers
import com.oscar0819.core.model.AlbumTrackInfo
import com.oscar0819.core.model.ArtistAlbumInfo
import com.oscar0819.core.model.ArtistSongInfo
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
            val albumTracks = network.lookupAlbumTracks(collectionId)

            val result: List<AlbumTrackInfo> = albumTracks.results.mapIndexed { index, item ->
                if (index == 0) {
                    upgradeArtworkUrl(
                        item = item.asExternalModel(),
                        artworkUrl100Getter = { item.artworkUrl100 },
                        copier = { original, newArtworkUrl1200 ->
                            original.copy(artworkUrl1200 = newArtworkUrl1200)
                        }
                    )
                } else {
                    item.asExternalModel()
                }
            }

            emit(result)
        }.flowOn(dispatchers.io)

    override fun lookupArtistAlbums(artistId: Int): Flow<List<ArtistAlbumInfo>> =
        flow {
            val artistAlbums = network.lookupArtistAlbums(artistId)

            val result: List<ArtistAlbumInfo> = artistAlbums.results.map { item ->
                upgradeArtworkUrl(
                    item = item.asExternalModel(),
                    artworkUrl100Getter = { item.artworkUrl100 },
                    copier = { original, newArtworkUrl1200 ->
                        original.copy(artworkUrl1200 = newArtworkUrl1200)
                    }
                )
            }

            emit(result)
        }.flowOn(dispatchers.io)

    override fun lookupArtistSongs(artistId: Int): Flow<List<ArtistSongInfo>> =
        flow {
            val artistSongs = network.lookupArtistSongs(artistId)

            val result: List<ArtistSongInfo> = artistSongs.results.map { item ->
                item.asExternalModel()
            }

            emit(result)
        }.flowOn(dispatchers.io)

    private fun <T> upgradeArtworkUrl(
        item: T,
        artworkUrl100Getter: (T) -> String?,
        copier: (original: T, newArtworkUrl1200: String?) -> T
    ): T {
        val artworkUrl100 = artworkUrl100Getter(item)
        val newArtworkUrl1200 = artworkUrl100?.replace(RESOLUTION_100, RESOLUTION_1200)

        return copier(item, newArtworkUrl1200)
    }

    companion object {
        const val RESOLUTION_100 = "100x100"
        const val RESOLUTION_1200 = "1200x1200"
    }
}