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
class SearchViewModel @Inject constructor(): ViewModel() {

    private val _searchTextFieldState = MutableStateFlow("yuuri")
    val searchTextFieldState: StateFlow<String>
        get() = _searchTextFieldState.asStateFlow()

    private val _searchTypeState = MutableStateFlow<SearchType?>(null)
    val searchTypeState: StateFlow<SearchType?>
        get() = _searchTypeState.asStateFlow()

    val searchTypeOptions = listOf(
        SearchType(id = 1, "Artist"),
        SearchType(id = 2, "Album")
    )

    init {
        _searchTypeState.value = searchTypeOptions[0]
    }

    fun updateSearchTextField(inputText: String) {
        _searchTextFieldState.value = inputText
    }

    fun updateSearchType(searchType: SearchType) {
        _searchTypeState.value = searchType
    }
}

data class SearchType(
    val id: Int,
    val name: String
)