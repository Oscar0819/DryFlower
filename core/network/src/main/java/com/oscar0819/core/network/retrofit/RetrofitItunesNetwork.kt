package com.oscar0819.core.network.retrofit

import androidx.core.os.trace
import com.oscar0819.core.network.BuildConfig
import com.oscar0819.core.network.ItunesNetworkDataSource
import com.oscar0819.core.model.NetworkAlbumResponse
import com.oscar0819.core.model.NetworkAlbumTrackResponse
import com.oscar0819.core.model.NetworkArtistResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface ItunesNetworkApi {
    @GET("search")
    suspend fun searchAlbum(
        @Query("term") searchTerm: String,
        @Query("entity") entity: String = "album",
        @Query("country") country: String = "US"
    ): NetworkAlbumResponse

    @GET("search")
    suspend fun searchArtist(
        @Query("term") searchTerm: String,
        @Query("entity") entity: String = "musicArtist",
        @Query("country") country: String = "US"
    ): NetworkArtistResponse

    @GET("lookup")
    suspend fun lookupAlbumTracks(
        @Query("id") collectionId: Int,
        @Query("entity") entity: String = "song"
    ): NetworkAlbumTrackResponse
}

private val ITUNES_BASE_URL = BuildConfig.ITUNES_URL
//private const val ITUNES_BASE_URL = "https://itunes.apple.com/"

@Serializable
private data class ItunesResponse<T>(
    val data: T,
)

@Singleton
internal class RetrofitItunesNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
): ItunesNetworkDataSource {

    private val networkApi = trace("RetrofitItunesNetwork") {
        Retrofit.Builder()
            .baseUrl(ITUNES_BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(ItunesNetworkApi::class.java)
    }

    override suspend fun searchAlbum(
        searchTerm: String,
        entity: String,
        country: String
    ): NetworkAlbumResponse =
        networkApi.searchAlbum(searchTerm, entity, country)

    override suspend fun searchArtist(
        searchTerm: String,
        entity: String,
        country: String
    ): NetworkArtistResponse =
        networkApi.searchArtist(searchTerm, entity, country)

    override suspend fun lookupAlbumTracks(
        collectionId: Int,
        entity: String
    ): NetworkAlbumTrackResponse =
        networkApi.lookupAlbumTracks(collectionId, entity)
}