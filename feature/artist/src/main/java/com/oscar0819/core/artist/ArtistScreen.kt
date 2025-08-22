package com.oscar0819.core.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
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
                    val pageCnt = calculatePageCount(
                        totalItemSize = uiState.artistSongInfos.size,
                        maxPageCount = 4,
                        itemPerPage = 4,
                    )

                    val topSongsPagerState = rememberPagerState(
                        pageCount = {
                            pageCnt
                        }
                    )

                    LazyColumn(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        artistName(uiState)
                        topSongs(uiState, topSongsPagerState)
                        album(uiState)
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
        ArtistCategoryText(R.string.top_songs)
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalPager(
            topSongsPagerState,
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) { page ->
            Column {
                repeat(4) { i ->
                    val index = page * 4 + i + 1 // 0번째는 더미 데이터라 1부터 시작

                    if (index >= uiState.artistSongInfos.size) return@repeat

                    val artistSongsInfos = uiState.artistSongInfos

                    val builder = ImageRequest.Builder(LocalContext.current)
                        .data(artistSongsInfos[index].artworkUrl100)
                        .crossfade(true)
                        .placeholder(com.oscar0819.core.preview.R.drawable.dryflower)

                    val imageRequest = builder.build()

                    val imageSize = 54

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillParentMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Card(
                            modifier = Modifier
                                .size(imageSize.dp)
                            ,
                            shape = RoundedCornerShape(6.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        ) {
                            AsyncImage(
                                model = imageRequest,
                                contentDescription = "앨범 이미지",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = "${artistSongsInfos[index].trackName}",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                modifier = Modifier.padding(end = 32.dp)
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = "${artistSongsInfos[index].collectionName}",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(end = 32.dp)
                            )
                        }
                    }
                    if (i != 3) {
                        HorizontalDivider(
                            Modifier.padding(start = (imageSize + 6).dp, end = 32.dp),
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                    }
                }
            }
        }
    }
}

private fun LazyListScope.album(
    uiState: ArtistUiState.Success,
) {
    item {
        ArtistCategoryText(R.string.album)

    }
}

@Composable
private fun ArtistCategoryText(id: Int) {
    Text(
        text = stringResource(id),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
    )
}

private fun calculatePageCount(
    totalItemSize: Int,
    maxPageCount: Int,
    itemPerPage: Int,
): Int {
    val size = totalItemSize - 1

    val existRemainder = if ((size % itemPerPage) != 0) true else false

    val pagesNeeded = size / itemPerPage

    val pageCnt = if (existRemainder) {
        min(pagesNeeded + 1, maxPageCount)
    } else {
        min(pagesNeeded, maxPageCount)
    }
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