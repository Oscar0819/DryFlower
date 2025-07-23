package com.oscar0819.dryflower

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oscar0819.designsystem.theme.DryFlowerTheme
import com.oscar0819.dryflower.navigation.DryFlowerScreen
import com.oscar0819.dryflower.navigation.dryflowerNavigation
import com.oscar0819.feature.search.SearchScreen

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DryFlowerTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = DryFlowerScreen.Search
                ) {
                    dryflowerNavigation(navController)
                }

//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//                }
            }
        }
    }
}