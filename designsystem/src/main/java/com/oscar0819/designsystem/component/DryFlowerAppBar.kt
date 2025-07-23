package com.oscar0819.designsystem.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.oscar0819.designsystem.theme.DryFlowerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DryFlowerAppBar(screenName: String) {
    TopAppBar(
        title = {
            Text(
                text = screenName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors()
    )
}

@Preview
@Composable
private fun DryFlowerAppBarPreview() {
    DryFlowerTheme {
        DryFlowerAppBar("DryFlower")
    }
}