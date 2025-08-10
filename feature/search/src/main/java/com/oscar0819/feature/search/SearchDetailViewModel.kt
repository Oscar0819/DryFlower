package com.oscar0819.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar0819.core.android.AppCoroutineDispatchers
import com.oscar0819.core.android.logger
import com.oscar0819.core.data.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val searchRepository: SearchRepository,
) : ViewModel() {

//    val uiState: StateFlow<SearchDetailUiState> = StateFlow(SearchDetailUiState.Loading)

    private val _searchTextFieldState = MutableStateFlow("")
    val searchTextFieldState: StateFlow<String>
        get() = _searchTextFieldState.asStateFlow()

    fun updateSearchTextFieldState(value: String) = viewModelScope.launch(dispatchers.io) {
        logger(value)
        _searchTextFieldState.value = value
    }
}

sealed class SearchDetailUiState {
    data object Success : SearchDetailUiState()
    data object Error : SearchDetailUiState()
    data object Loading : SearchDetailUiState()
}