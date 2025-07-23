package com.oscar0819.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToAlbums: () -> Unit,
) {
    val searchInputText by viewModel.searchTextFieldState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Dry Flower")
        SearchTextField(
            searchInputText,
            onSearchInputTextChanged = { inputText ->
                viewModel.updateSearchTextField(inputText)
            },
            onSearch = {
                viewModel.search({ onNavigateToAlbums() })
            }
        )
    }
}

@Composable
fun SearchTextField(
    searchInputText: String,
    onSearchInputTextChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        value = searchInputText,
        onValueChange = { inputText ->
            onSearchInputTextChanged(inputText)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        ),
        label = { Text("search") }
    )
}

@Preview
@Composable
private fun SearchScreenPreview() {

}