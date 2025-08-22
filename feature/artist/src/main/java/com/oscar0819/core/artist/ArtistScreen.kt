package com.oscar0819.core.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oscar0819.core.preview.PreviewUtils
import com.oscar0819.designsystem.component.DryFlowerAppBar
import com.oscar0819.designsystem.component.DryFlowerCircularProgress
import kotlin.math.max
import kotlin.math.min

@Composable
fun ArtistScreen(
    onShowSnackbar: suspend (String, String?) -> Unit,
    artistViewModel: ArtistViewModel = hiltViewModel(),
) {
    val uiState by artistViewModel.uiState.collectAsStateWithLifecycle()

    ArtistScreen(
        onShowSnackbar = onShowSnackbar,
        uiState = uiState,
    )
}

@Composable
fun ArtistScreen(
    onShowSnackbar: suspend (String, String?) -> Unit,
    uiState : ArtistUiState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DryFlowerAppBar(
            stringResource(R.string.artist)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            when (uiState) {
                is ArtistUiState.Success -> {
                    val songVisibilityCnt = 4
                    val maxPageCnt = 4
                    val pageCnt = getPageCount(
                        uiState.artistSongInfos.size,
                        maxPageCnt,
                        songVisibilityCnt,
                    )

                    val topSongsPagerState = rememberPagerState(pageCount = { pageCnt })

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        artistName(uiState)
                        topSongs(uiState, topSongsPagerState)
                    }
                }
                ArtistUiState.Loading -> {
                    DryFlowerCircularProgress()
                }
                ArtistUiState.Error -> {
                    val errorMessage = stringResource(com.oscar0819.designsystem.R.string.network_error)
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = errorMessage
                    )
                    LaunchedEffect(Unit) {
                        onShowSnackbar(errorMessage, null)
                    }
                }
            }
        }
    }
}

private fun LazyListScope.artistName(uiState: ArtistUiState.Success) {
    item {
        Text(
            text = uiState.artistAlbumInfos.firstOrNull()?.artistName ?: "null",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

private fun LazyListScope.topSongs(
    uiState: ArtistUiState.Success,
    topSongsPagerState: PagerState,
) {
    item {
        // TODO 행 갯수로 표현할 수 없는 음악은 화살표로 전체 볼 수 있게 하기
        Text(
            text = stringResource(R.string.top_songs),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        HorizontalPager(topSongsPagerState) { page ->
            Column {
                repeat(4) { i ->
                    val index = page * 4 + i + 1 // 첫번째

                    if (index >= uiState.artistSongInfos.size) return@repeat

                    val artistSongsInfos = uiState.artistSongInfos

                    val builder = ImageRequest.Builder(LocalContext.current)
                        .data(artistSongsInfos[index].artworkUrl100)
                        .crossfade(true)
                        .placeholder(com.oscar0819.core.preview.R.drawable.dryflower)

                    val imageRequest = builder.build()

                    Row(modifier = Modifier.fillParentMaxWidth()) {
                        AsyncImage(
                            model = imageRequest,
                            contentDescription = "앨범 이미지",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(54.dp)
                        )

                        Column {
                            Text(
                                text = "${artistSongsInfos[index].trackName}",
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "${artistSongsInfos[index].collectionName}",
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getPageCount(
    artistSongInfoListSize: Int,
    maxPageCount: Int,
    maxItemVisibilityCnt: Int,
): Int {
    val size = artistSongInfoListSize - 1

    val existRemainder = if ((size % maxItemVisibilityCnt) != 0) true else false

    val pagesNeeded = size / maxItemVisibilityCnt

    val pageCnt = if (existRemainder) {
        min(pagesNeeded + 1, maxPageCount)
    } else {
        min(pagesNeeded, maxPageCount)
    }
    // 기본적으로 1페이지를 필요로 함.
    return pageCnt
}

@Preview
@Composable
fun ArtistScreenPreview() {
    val artistAlbumInfos = PreviewUtils.mockArtistAlbums()
    val artistSongInfos = PreviewUtils.mockArtistSongs()
    ArtistScreen(
        onShowSnackbar = {_, _ -> },
        uiState = ArtistUiState.Success(
            artistAlbumInfos,
            artistSongInfos,
        )
    )
}