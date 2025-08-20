package com.oscar0819.feature.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar0819.core.android.CoroutineDispatchers
import com.oscar0819.core.android.logger
import com.oscar0819.core.data.repo.LookupRepository
import com.oscar0819.core.model.AlbumTrackInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val lookupRepository: LookupRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    // TODO key 값 상수로 관리.
    private val collectionId = savedStateHandle.getStateFlow<Int?>("collectionId", null)

    val uiState: StateFlow<AlbumTrackUiState> = collectionId.filterNotNull().flatMapLatest { collectionId ->
        flow {
            val result = try {
                val tracks = lookupRepository.lookupAlbumTracks(collectionId).first()
                AlbumTrackUiState.Success(tracks)
            } catch (e: Exception) {
                e.printStackTrace()
                AlbumTrackUiState.Error
            }
            emit(result)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AlbumTrackUiState.Loading,
    )
}

sealed interface AlbumTrackUiState {
    data class Success(val albumTrackInfoList: List<AlbumTrackInfo>) : AlbumTrackUiState
    data object Error : AlbumTrackUiState
    data object Loading : AlbumTrackUiState
}