package com.oscar0819.feature.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar0819.core.android.AppCoroutineDispatchers
import com.oscar0819.core.android.logger
import com.oscar0819.core.data.repo.AlbumsRepository
import com.oscar0819.core.model.AlbumInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val albumsRepository: AlbumsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val term = savedStateHandle.getStateFlow<String?>("term", null)

    val uiState: StateFlow<AlbumUiState> = term.filterNotNull().flatMapLatest { term ->
        flow {
            try {
                val albums = albumsRepository.searchAlbum(term).first()
                emit(AlbumUiState.Success(albums))
            } catch (e: Exception) {
                emit(AlbumUiState.Error)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AlbumUiState.Loading,
    )

}

sealed interface AlbumUiState {
    data class Success(val albumInfoList: List<AlbumInfo>) : AlbumUiState
    data object Error : AlbumUiState
    data object Loading : AlbumUiState
}