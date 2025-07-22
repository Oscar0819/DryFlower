package com.oscar0819.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar0819.core.android.AppCoroutineDispatchers
import com.oscar0819.core.android.logger
import com.oscar0819.core.data.repo.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun updateSearchTextField(inputText: String) {
        _searchTextFieldState.value = inputText
    }

    fun search() = viewModelScope.launch(dispatchers.io) {
        val searchText = _searchTextFieldState.value
        logger("search : ${searchText}")

        val flow = albumsRepository.searchAlbum(searchText)

        flow.collect {
            logger("collect $it")
        }
    }
}