package com.oscar0819.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar0819.core.android.AppCoroutineDispatchers
import com.oscar0819.core.android.logger
import com.oscar0819.core.data.repo.SearchRepository
import com.oscar0819.core.model.AlbumInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchDetailUiState>(SearchDetailUiState.Loading)
    val uiState: StateFlow<SearchDetailUiState>
        get() = _uiState.asStateFlow()

    private val _searchTextFieldState = MutableStateFlow("")
    val searchTextFieldState: StateFlow<String>
        get() = _searchTextFieldState.asStateFlow()

    var searchJob: Job? = null

    fun updateSearchTextFieldState(value: String) {
        logger(value)
        _searchTextFieldState.value = value

        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatchers.io) {
            delay(500)

            val albums = try {
                SearchDetailUiState.Success(
                    searchRepository.searchAlbum(value).first()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                SearchDetailUiState.Error
            }

            if (isActive) {
                _uiState.value = albums
            }
        }
    }

}

sealed class SearchDetailUiState {
    data class Success(val albumInfoList: List<AlbumInfo>) : SearchDetailUiState()
    data object Error : SearchDetailUiState()
    data object Loading : SearchDetailUiState()
}