package com.oscar0819.feature.album

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.oscar0819.designsystem.component.DryFlowerAppBar

@Composable
fun AlbumScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        DryFlowerAppBar(stringResource(R.string.screen_name))

    }
}