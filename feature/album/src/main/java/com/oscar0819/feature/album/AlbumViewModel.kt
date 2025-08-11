package com.oscar0819.feature.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar0819.core.android.AppCoroutineDispatchers
import com.oscar0819.core.data.repo.SearchRepository
import com.oscar0819.core.model.AlbumInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val searchRepository: SearchRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val term = savedStateHandle.getStateFlow<String?>("term", null)

    val uiState: StateFlow<AlbumUiState> = term.filterNotNull().flatMapLatest { term ->
        flow {
            // TODO 네트워크 통신만 try catch
            try {
                val albums = searchRepository.searchAlbum(term).first()
                emit(AlbumUiState.Success(albums))
            } catch (e: Exception) {
                e.printStackTrace()
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