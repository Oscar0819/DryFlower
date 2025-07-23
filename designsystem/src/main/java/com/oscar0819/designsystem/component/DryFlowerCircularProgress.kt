package com.oscar0819.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oscar0819.designsystem.theme.DryFlowerTheme

@Composable
fun BoxScope.DryFlowerCircularProgress() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center),
    )
}

@Preview
@Composable
private fun DryFlowerCircularProgressPreview() {
    DryFlowerTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            DryFlowerCircularProgress()
        }
    }
}