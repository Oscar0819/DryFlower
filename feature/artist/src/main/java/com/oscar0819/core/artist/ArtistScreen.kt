package com.oscar0819.core.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import com.oscar0819.designsystem.component.DryFlowerAppBar
import com.oscar0819.designsystem.component.DryFlowerCircularProgress

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
    uiState : ArtistUiState
) {
    val testState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(testState)
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
                    Text(uiState.artistInfo.toString())
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

@Preview
@Composable
fun ArtistScreenPreview() {
    ArtistScreen(
        onShowSnackbar = {_, _ -> },
        uiState = ArtistUiState.Success(emptyList())
    )
}