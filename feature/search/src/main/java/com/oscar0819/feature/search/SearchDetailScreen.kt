package com.oscar0819.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SearchDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToNextScreen: (String, SearchType?) -> Unit,
) {
    val searchInputText by viewModel.searchTextFieldState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(MaterialTheme.colorScheme.background),
    ) {
        SearchDetailContent(
            searchInputText
        )
    }

}

@Composable
fun SearchDetailContent(
    searchInputText: String
) {
    OutlinedTextField(
        value = searchInputText,
        onValueChange = { },
        label = { Text("search") },

    )
}

@Preview
@Composable
fun SearchDetailPreview() {
    SearchDetailScreen(onNavigateToNextScreen = { a, b ->

    })
}