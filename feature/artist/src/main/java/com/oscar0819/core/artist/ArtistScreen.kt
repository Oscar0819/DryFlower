package com.oscar0819.core.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.oscar0819.designsystem.component.DryFlowerAppBar

@Composable
fun ArtistScreen(
    artistViewModel: ArtistViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DryFlowerAppBar(
            stringResource(R.string.screen_name)
        )
        ArtistContent()
    }
}

@Composable
fun ArtistContent() {
    Text("Yuuri")
}

@Preview
@Composable
fun ArtistScreenPreview() {
    ArtistScreen()
}