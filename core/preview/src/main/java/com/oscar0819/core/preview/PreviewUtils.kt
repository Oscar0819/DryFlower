package com.oscar0819.core.preview

import com.oscar0819.core.model.AlbumInfo
import com.oscar0819.core.model.AlbumTrackInfo
import com.oscar0819.core.model.NetworkAlbumResponse
import com.oscar0819.core.model.NetworkAlbumTrackResponse
import com.oscar0819.core.model.asExternalModel
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException

// pokedex-compose 참고 구현
object PreviewUtils {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = false
    }

    private val mockAlbums: List<AlbumInfo> by lazy {
        try {
            val networkResponse =  json.decodeFromString<NetworkAlbumResponse>(getStringFromJsonFile("albums.json"))

            val result: List<AlbumInfo> = networkResponse.results.map {
                it.asExternalModel()
//                changeUrl(externalModel)
            }

            result
        } catch (e: Exception) {
            e.printStackTrace()

            emptyList()
        }
    }

    private val mockAlbumTracks: List<AlbumTrackInfo> by lazy {
        try {
            val networkResponse = json.decodeFromString<NetworkAlbumTrackResponse>("album_tracks.json")

            val result: List<AlbumTrackInfo> = networkResponse.results.map {
                it.asExternalModel()
            }

            result
        } catch (e: Exception) {
            e.printStackTrace()

            val list: MutableList<AlbumTrackInfo> = mutableListOf()
            list.add(AlbumTrackInfo(trackName = "occured error"))
            list
        }
    }

    private fun getStringFromJsonFile(fileName: String): String {
        return try {
            val inputStream = PreviewUtils::class.java.classLoader?.getResourceAsStream(fileName)
            if (inputStream == null) {
                throw FileNotFoundException("resource file not found: $fileName")
            }
            inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }



    fun mockAlbumList() = mockAlbums

    fun mockAlbumTrackList() = mockAlbumTracks
}