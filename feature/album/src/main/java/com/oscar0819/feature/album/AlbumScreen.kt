package com.oscar0819.feature.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oscar0819.core.model.AlbumInfo
import com.oscar0819.designsystem.component.DryFlowerAppBar
import com.oscar0819.designsystem.component.DryFlowerCircularProgress

@Composable
fun AlbumScreen(
    albumViewModel: AlbumViewModel = hiltViewModel()
) {
    val uiState by albumViewModel.uiState.collectAsStateWithLifecycle()
    val albumInfoList by albumViewModel.albumInfoList.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DryFlowerAppBar(
            stringResource(R.string.screen_name)
        )
        AlbumContent(
            albumViewModel,
            uiState,
            albumInfoList
        )
    }
}

@Composable
private fun AlbumContent(
    albumViewModel: AlbumViewModel,
    uiState: AlbumUiState,
    albumInfoList: List<AlbumInfo>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(6.dp),
        ) {
            itemsIndexed(albumInfoList) { index, albumInfo ->
                // 스크롤에 따라 새로운 아이템을 그리기 위해 이 블록이 재실행됨.
                AlbumCard(albumInfo)
            }
        }

        when (uiState) {
            AlbumUiState.Loading -> {
                DryFlowerCircularProgress()
            }
            AlbumUiState.Error(null) -> {
                // onError 발생 후 바로 onCompletion 발생으로 안 나타나는 것 처럼 보임.
                Text("Occurred Network Error")
            }
            else -> { }
        }
//        if (uiState == AlbumUiState.Loading) {
//            DryFlowerCircularProgress()
//        } else if (uiState == AlbumUiState.Error(null)) {
//            Text("Occurred Network Error")
//        }
    }
}

@Composable
private fun AlbumCard(
    albumInfo: AlbumInfo,
) {

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(albumInfo.artworkUrl1200)
                .crossfade(true)
                .placeholder(null)
                .build(),
            contentDescription = "앨범 이미지",
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview
@Composable
fun AlbumScreenPreview() {
    AlbumScreen()
}
