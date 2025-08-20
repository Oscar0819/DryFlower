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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oscar0819.core.android.logger
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
        onNavigateToNextScreen,
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
    onNavigateToNextScreen: (String, SearchType?) -> Unit,
    updateSearchTextFieldState: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animationVisibilityScope: AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .background(Color.LightGray), // TODO 다크모드 수정
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        with(sharedTransitionScope) {
            val focusRequester = remember { FocusRequester() }
            TextField(
                value = searchInputText,
                onValueChange = { value ->
                    updateSearchTextFieldState(value)
                },
                placeholder = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp, 8.dp, 0.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .sharedElement(
                        rememberSharedContentState(key = "search"),
                        animatedVisibilityScope = animationVisibilityScope
                    )
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.colors( //
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
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
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(8.dp)
        ) {
            searchDetailBody(uiState, onNavigateToNextScreen)
        }
    }
}

// NIA 참고. 컴포저블이 아닌 형태로 Preview를 보여주기 위함?
private fun LazyListScope.searchDetailBody(
    uiState: SearchDetailUiState,
    onNavigateToNextScreen: (String, SearchType?) -> Unit,
) {
    if (uiState is SearchDetailUiState.Success) {
        itemsIndexed(uiState.albumInfoList) { index, albumInfo ->
            AlbumItem(albumInfo = albumInfo, onNavigateToNextScreen)
            if (index < uiState.albumInfoList.lastIndex) {
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            }
        }
    }
}

@Composable
fun AlbumItem(
    albumInfo: AlbumInfo,
    onNavigateToNextScreen: (String, SearchType?) -> Unit,
) {
    albumInfo.collectionName?.let {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .clickable {
                    logger("onClick")
                    onNavigateToNextScreen(
                        albumInfo.collectionId.toString(),
                        SearchType(
                            id = 3,
                            name = "AlbumTrack"
                        )
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(albumInfo.artworkUrl100)
                    .crossfade(true)
                    .placeholder(null)
                    .build(),
                contentDescription = "앨범 이미지",
                modifier = Modifier.size(48.dp)
                    .clip(RoundedCornerShape(6.dp)),
            )

            Spacer(Modifier.width(8.dp))

            Column {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    maxLines = 1,
                )
                Text(
                    text = "Artist : ${albumInfo.artistName}",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    maxLines = 1,
                )
            }
        }
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
                    onNavigateToNextScreen = { _, _ ->
                    /* Preview에서는 동작 없음 */
                    },
                    updateSearchTextFieldState = { _ ->
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