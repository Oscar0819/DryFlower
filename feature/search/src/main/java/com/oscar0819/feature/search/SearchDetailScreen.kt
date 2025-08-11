package com.oscar0819.feature.search

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oscar0819.core.model.AlbumInfo

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SearchDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchDetailViewModel = hiltViewModel(),
    onNavigateToNextScreen: (String, SearchType?) -> Unit,
    animationVisibilityScope: AnimatedVisibilityScope
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchInputText by viewModel.searchTextFieldState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchDetailContent(
            viewModel,
            uiState,
            searchInputText,
            sharedTransitionScope = this@SearchDetailScreen,
            animationVisibilityScope = animationVisibilityScope
        )
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchDetailContent(
    viewModel: SearchDetailViewModel,
    uiState: SearchDetailUiState,
    searchInputText: String,
    sharedTransitionScope: SharedTransitionScope,
    animationVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        val focusRequester = remember { FocusRequester() }
        OutlinedTextField(
            value = searchInputText,
            onValueChange = { value ->
                viewModel.updateSearchTextFieldState(value)
            },
            label = { Text("search") },
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(key = "search"),
                    animatedVisibilityScope = animationVisibilityScope
                )
                .focusRequester(focusRequester),
        )

        // TODO 추가 후 애니메이션 버벅임 생김 확인 필요
        // 컴포저블이 처음 나타날 때 포커스 요청
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    SearchDetailList(uiState)
}

@Composable
fun SearchDetailList(
    uiState: SearchDetailUiState,
) {

    if (uiState is SearchDetailUiState.Success) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(uiState.albumInfoList) { albumInfo ->
                AlbumItem(albumInfo = albumInfo)
            }
        }
    }
}

@Composable
fun AlbumItem(albumInfo: AlbumInfo) {
    albumInfo.collectionName?.let {
        Text(text = it)
    }
}


@Preview
@Composable
fun SearchDetailPreview() {
//    SearchDetailScreen(onNavigateToNextScreen = {},)
}