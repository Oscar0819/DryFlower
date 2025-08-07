package com.oscar0819.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToSearchDetail: () -> Unit,
) {
    val searchInputText by viewModel.searchTextFieldState.collectAsStateWithLifecycle()
    val searchType by viewModel.searchTypeState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchContent(searchInputText, searchType, viewModel, onNavigateToSearchDetail)
    }
}

@Composable
fun SearchContent(
    searchInputText: String,
    searchType: SearchType?,
    viewModel: SearchViewModel,
    onNavigateToSearchDetail: () -> Unit,
) {
    Text("Dry Flower")
    RadioButtonGroup(searchType, viewModel)
    SearchTextField(
        searchInputText,
        onNavigateToSearchDetail = {
            onNavigateToSearchDetail()
        }
    )
}

@Composable
fun RadioButtonGroup(
    searchType: SearchType?,
    viewModel: SearchViewModel,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        viewModel.searchTypeOptions.forEach { option ->
            RadioButton(
                selected = (searchType == option),
                onClick = { viewModel.updateSearchType(option) }
            )
            Text(option.name)
        }
    }
}

@Composable
fun SearchTextField(
    searchInputText: String,
    onNavigateToSearchDetail: () -> Unit,
) {
    OutlinedTextField(
        value = searchInputText,
        onValueChange = { },
        label = { Text("search") },
        readOnly = true,
        modifier = Modifier.onFocusChanged { focusState ->
            if (focusState.isFocused) {
                onNavigateToSearchDetail()
            }
        }
    )
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(onNavigateToSearchDetail = { })
}