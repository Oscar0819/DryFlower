package com.oscar0819.feature.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {

    private val _searchTypeState = MutableStateFlow<SearchType?>(null)
    val searchTypeState: StateFlow<SearchType?>
        get() = _searchTypeState.asStateFlow()

    val searchTypeOptions = listOf(
        SearchType(id = 1, "Artist"),
        SearchType(id = 2, "Album"),
        SearchType(id = 3, "AlbumTrack"),
    )

    init {
        _searchTypeState.value = searchTypeOptions[0]
    }

    fun updateSearchType(searchType: SearchType) {
        _searchTypeState.value = searchType
    }
}

data class SearchType(
    val id: Int,
    val name: String
)