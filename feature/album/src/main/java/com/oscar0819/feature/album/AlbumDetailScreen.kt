package com.oscar0819.feature.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oscar0819.core.android.logger
import com.oscar0819.core.model.AlbumTrackInfo
import com.oscar0819.core.preview.PreviewUtils
import com.oscar0819.designsystem.component.DryFlowerAppBar
import com.oscar0819.designsystem.component.DryFlowerCircularProgress

@Composable
fun AlbumDetailScreen(
    onShowSnackbar: suspend (String, String?) -> Unit,
    albumDetailViewModel: AlbumDetailViewModel = hiltViewModel(),
) {
    val uiState by albumDetailViewModel.uiState.collectAsStateWithLifecycle()

    AlbumDetailScreen(
        onShowSnackbar = onShowSnackbar,
        uiState = uiState,
    )
}

@Composable
fun AlbumDetailScreen(
    onShowSnackbar: suspend (String, String?) -> Unit,
    uiState: AlbumTrackUiState,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.navigationBars)
        .background(Color.LightGray), // TODO 다크모드 수정
    ) {
        DryFlowerAppBar("")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            when (uiState) {
                is AlbumTrackUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        // TODO dp 값 효율적으로 배치하는 방법
                        albumCard(uiState)
                        trackList(uiState)
                    }
                }
                AlbumTrackUiState.Loading -> {
                    DryFlowerCircularProgress()
                }
                AlbumTrackUiState.Error -> {
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

private fun LazyListScope.albumCard(uiState: AlbumTrackUiState.Success) {
    val albumInfo: AlbumTrackInfo? = uiState.albumTrackInfoList.firstOrNull()

    albumInfo?.let {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val builder = ImageRequest.Builder(LocalContext.current)
                    .data(albumInfo.artworkUrl1200)
                    .crossfade(true)
                    .placeholder(null)

                if (albumInfo.artworkUrl1200.isNullOrEmpty()) {
                    builder.placeholder(com.oscar0819.core.preview.R.drawable.dryflower)
                }

                val imageRequest = builder.build()
                // TODO 사이즈를 더 좋게 잡을 수 있는 방법 찾기
                Card(
                    modifier = Modifier
                        .fillMaxWidth() // TODO 가로모드 고려
                        .padding(48.dp, 48.dp, 48.dp, 0.dp)
                    ,
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = "앨범 이미지",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)

                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = albumInfo.collectionName.toString(),
                    fontSize = 18.sp,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = albumInfo.artistName.toString(),
                    fontSize = 18.sp,
                    color = Color.Red
                )
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(Modifier.padding(start = 16.dp), DividerDefaults.Thickness, DividerDefaults.color)
            }
        }
    }
}

private fun LazyListScope.trackList(uiState: AlbumTrackUiState.Success) {
    itemsIndexed(uiState.albumTrackInfoList) { index, trackInfo ->
        if (index == 0) {
            return@itemsIndexed
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "${trackInfo.trackNumber.toString()}.",
                color = Color.Gray,
                maxLines = 1,
            )
            Spacer(Modifier.width(8.dp))
            Text(
                trackInfo.trackName.toString(),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        HorizontalDivider(Modifier.padding(start = 16.dp), DividerDefaults.Thickness, DividerDefaults.color)
    }
}

@Preview
@Composable
fun AlbumDetailPreview() {
    logger(PreviewUtils.mockAlbumTracks().toString())
    AlbumDetailScreen(
        onShowSnackbar = { _, _ -> },
        uiState = AlbumTrackUiState.Success(PreviewUtils.mockAlbumTracks()),
    )
}