package com.oscar0819.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar0819.core.android.AppCoroutineDispatchers
import com.oscar0819.core.android.logger
import com.oscar0819.core.data.repo.AlbumsRepository
import com.oscar0819.core.model.AlbumInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val albumsRepository: AlbumsRepository
): ViewModel() {

    private val _searchTextFieldState = MutableStateFlow("")
    val searchTextFieldState: StateFlow<String>
        get() = _searchTextFieldState.asStateFlow()

    private val _searchResults = MutableStateFlow<List<AlbumInfo>>(emptyList())
    val searchResults: StateFlow<List<AlbumInfo>>
        get() = _searchResults.asStateFlow()

    fun updateSearchTextField(inputText: String) {
        _searchTextFieldState.value = inputText
    }

    fun search(onSearchComplete: () -> Unit) = viewModelScope.launch(dispatchers.io) {
        try {
            val searchText = _searchTextFieldState.value
            logger("search : $searchText")

            val results = albumsRepository.searchAlbum(searchText).first()
            launch(dispatchers.main) {
                _searchResults.value = results
                onSearchComplete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // loading = false
        }
    }
}