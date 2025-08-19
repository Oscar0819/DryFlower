package com.oscar0819.feature.album

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oscar0819.core.preview.PreviewUtils
import com.oscar0819.designsystem.component.DryFlowerCircularProgress
import com.oscar0819.designsystem.theme.DryFlowerTheme

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
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is AlbumTrackUiState.Success -> {
                Text(uiState.albumInfoList.toString())
            }
            AlbumTrackUiState.Loading -> {
                DryFlowerCircularProgress()
            }
            AlbumTrackUiState.Error -> {
                val errorMessage = stringResource(R.string.network_error)
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

@Preview
@Composable
fun AlbumDetailPreview() {
    AlbumDetailScreen(
        onShowSnackbar = { _, _ -> },
        uiState = AlbumTrackUiState.Success(emptyList()), // TODO
    )
}