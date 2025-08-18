package com.oscar0819.feature.search

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import com.oscar0819.core.android.AppCoroutineDispatchers
import com.oscar0819.core.android.FakeAppCoroutineDispatchers
import com.oscar0819.core.data.repo.FakeSearchRepository
import com.oscar0819.core.data.repo.MockSearchRepository
import com.oscar0819.core.model.AlbumInfo
import com.oscar0819.core.preview.PreviewUtils

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

    SearchDetailScreen(
        uiState,
        searchInputText,
        viewModel::updateSearchTextFieldState,
        sharedTransitionScope = this@SearchDetailScreen,
        animationVisibilityScope = animationVisibilityScope
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@VisibleForTesting
@Composable
internal fun SharedTransitionScope.SearchDetailScreen(
    uiState: SearchDetailUiState,
    searchInputText: String,
    updateSearchTextFieldState: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animationVisibilityScope: AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        with(sharedTransitionScope) {
            val focusRequester = remember { FocusRequester() }
            OutlinedTextField(
                value = searchInputText,
                onValueChange = { value ->
                    updateSearchTextFieldState(value)
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            searchDetailBody(uiState)
        }
    }
}

// NIA 참고. 컴포저블이 아닌 형태로 Preview를 보여주기 위함?
private fun LazyListScope.searchDetailBody(
    uiState: SearchDetailUiState,
) {
    if (uiState is SearchDetailUiState.Success) {
        items(uiState.albumInfoList) { albumInfo ->
            AlbumItem(albumInfo = albumInfo)
        }
    }
}

@Composable
fun AlbumItem(albumInfo: AlbumInfo) {
    albumInfo.collectionName?.let {
        Text(text = it)
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun SearchDetailPreview() {
    MaterialTheme {
        SharedTransitionLayout {
            AnimatedVisibility(
                visible = true,
                label = "SearchScreenPreviewAnimatedVisibility", // 애니메이션 디버깅에 유용합니다.
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                // SharedTransitionLayout의 SharedTransitionScope를 SearchScreen의 receiver로 사용하고,
                // AnimatedVisibility의 AnimatedVisibilityScope를 파라미터로 전달합니다.
                SearchDetailScreen(
                    // Fake Repository 방식은 Pokedex-compose 프로젝트 참고
                    uiState = SearchDetailUiState.Success(PreviewUtils.mockAlbumList()),
                    searchInputText = "",
                    updateSearchTextFieldState = { str ->
                    /* Preview에서는 동작 없음 */
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animationVisibilityScope = this // AnimatedVisibility의 scope
                    // viewModel은 기본 hiltViewModel()을 사용합니다.
                    // Preview 환경에서 Hilt ViewModel 생성에 문제가 있다면,
                    // 별도의 Fake ViewModel을 제공해야 할 수 있습니다.
                )
            }
        }
    }
}