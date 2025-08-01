package com.oscar0819.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToNextScreen: (String, SearchType?) -> Unit,
) {
    val searchInputText by viewModel.searchTextFieldState.collectAsStateWithLifecycle()
    val searchType by viewModel.searchTypeState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchContent(searchInputText, searchType, viewModel, onNavigateToNextScreen)
    }
}

@Composable
fun SearchContent(
    searchInputText: String,
    searchType: SearchType?,
    viewModel: SearchViewModel,
    onNavigateToNextScreen: (String, SearchType?) -> Unit,
) {
    Text("Dry Flower")
    RadioButtonGroup(searchType, viewModel)
    SearchTextField(
        searchInputText,
        onSearchInputTextChanged = { inputText ->
            viewModel.updateSearchTextField(inputText)
        },
        onSearch = {
            val encodedTerm = searchInputText.replace(" ", "+")
            onNavigateToNextScreen(encodedTerm, searchType)
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
    SearchScreen(onNavigateToNextScreen = { a, b ->

    })
}