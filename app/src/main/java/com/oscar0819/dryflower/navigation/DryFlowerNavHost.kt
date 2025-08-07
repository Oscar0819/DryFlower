package com.oscar0819.dryflower.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DryFlowerNavHost(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
) {
    SharedTransitionLayout {
        NavHost(
            navController = navHostController,
            startDestination = DryFlowerScreen.Search
        ) {
            dryflowerNavigation(
                this@SharedTransitionLayout,
                navHostController,
                onShowSnackbar = { message, action ->
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = action,
                        duration = SnackbarDuration.Short
                    )
                }
            )
        }
    }
}