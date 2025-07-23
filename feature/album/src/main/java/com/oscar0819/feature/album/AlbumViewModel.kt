package com.oscar0819.feature.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar0819.core.android.AppCoroutineDispatchers
import com.oscar0819.core.data.repo.AlbumsRepository
import com.oscar0819.core.model.AlbumInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val albumsRepository: AlbumsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    internal val uiState: StateFlow<AlbumUiState> = MutableStateFlow(AlbumUiState.Loading)

    private val term = savedStateHandle.getStateFlow<String?>("term", null)
    val albumInfoList: StateFlow<List<AlbumInfo>> =
        term.filterNotNull().flatMapLatest { term ->
            albumsRepository.searchAlbum(term)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

}

internal sealed interface AlbumUiState {

    data object Idle : AlbumUiState

    data object Loading : AlbumUiState

    data class Error(val message: String?) : AlbumUiState
}