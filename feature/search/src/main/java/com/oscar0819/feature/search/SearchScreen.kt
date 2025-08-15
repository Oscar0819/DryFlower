package com.oscar0819.feature.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToSearchDetail: () -> Unit,
    animationVisibilityScope: AnimatedVisibilityScope
) {
    val searchType by viewModel.searchTypeState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchContent(
            searchType,
            viewModel,
            onNavigateToSearchDetail,
            this@SearchScreen,
            animationVisibilityScope
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchContent(
    searchType: SearchType?,
    viewModel: SearchViewModel,
    onNavigateToSearchDetail: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animationVisibilityScope: AnimatedVisibilityScope
) {
    val searchHint = stringResource(id = R.string.search_hint)
    Text("Dry Flower")
    RadioButtonGroup(searchType, viewModel)
    SearchTextField(
        searchHint,
        onNavigateToSearchDetail = {
            onNavigateToSearchDetail()
        },
        sharedTransitionScope,
        animationVisibilityScope
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchTextField(
    searchInputText: String,
    onNavigateToSearchDetail: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animationVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        OutlinedTextField(
            value = searchInputText,
            onValueChange = { },
            label = { Text("search") },
            readOnly = true,
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(key = "search"),
                    animatedVisibilityScope = animationVisibilityScope
                )
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        onNavigateToSearchDetail()
                    }
                }
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
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
                this@SharedTransitionLayout.SearchScreen(
                    onNavigateToSearchDetail = { /* Preview에서는 동작 없음 */ },
                    animationVisibilityScope = this // AnimatedVisibility의 scope
                    // viewModel은 기본 hiltViewModel()을 사용합니다.
                    // Preview 환경에서 Hilt ViewModel 생성에 문제가 있다면,
                    // 별도의 Fake ViewModel을 제공해야 할 수 있습니다.
                )
            }
        }
    }
}
