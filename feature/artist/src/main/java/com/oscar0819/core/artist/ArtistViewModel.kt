package com.oscar0819.core.artist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar0819.core.android.CoroutineDispatchers
import com.oscar0819.core.data.repo.LookupRepository
import com.oscar0819.core.model.ArtistAlbumInfo
import com.oscar0819.core.model.ArtistSongInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val lookupRepository: LookupRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val artistId = savedStateHandle.getStateFlow<Int?>("artistId", null)

    val uiState: StateFlow<ArtistUiState> =  artistId.filterNotNull().flatMapLatest { artistId ->
        combine(
            lookupRepository.lookupArtistAlbums(artistId),
            lookupRepository.lookupArtistSongs(artistId),
        ) { artistAlbums, artistSongs ->
            ArtistUiState.Success(
                artistAlbums,
                artistSongs
            )
        }.catch {
            ArtistUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ArtistUiState.Loading,
    )
}

sealed class ArtistUiState {
    data class Success(
        val artistAlbumInfos: List<ArtistAlbumInfo>,
        val artistSongInfos: List<ArtistSongInfo>
    ) : ArtistUiState()

    data object Error : ArtistUiState()
    data object Loading : ArtistUiState()
}

