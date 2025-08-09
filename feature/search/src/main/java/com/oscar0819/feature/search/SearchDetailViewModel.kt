package com.oscar0819.feature.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor() : ViewModel() {
    private val _searchTextFieldState = MutableStateFlow("")
    val searchTextFieldState: StateFlow<String>
        get() = _searchTextFieldState.asStateFlow()


}