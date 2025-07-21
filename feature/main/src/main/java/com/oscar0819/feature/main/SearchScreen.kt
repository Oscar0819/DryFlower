package com.oscar0819.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.oscar0819.core.android.logger

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val searchInputText by viewModel.searchTextFieldState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Dry Flower")
        SearchTextField(
            searchInputText,
            onSearchInputTextChanged = { inputText ->
                logger(inputText)
                viewModel.updateSearchTextField(inputText)
            }
        )
    }
}

@Composable
fun SearchTextField(
    searchInputText: String,
    onSearchInputTextChanged: (String) -> Unit,
) {
    OutlinedTextField(
        value = searchInputText,
        onValueChange = { inputText ->
            onSearchInputTextChanged(inputText)
        },
        label = { Text("search") }
    )
}

@Preview
@Composable
private fun SearchScreenPreview() {

}