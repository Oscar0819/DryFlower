package com.oscar0819.dryflower.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.oscar0819.core.artist.ArtistScreen
import com.oscar0819.feature.album.AlbumScreen
import com.oscar0819.feature.search.SearchScreen

fun NavGraphBuilder.dryflowerNavigation(
    navHostController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Unit,
) {
    composable<DryFlowerScreen.Search> {
        SearchScreen(
            onNavigateToNextScreen = { term, searchType ->
                when (searchType?.id) {
                    1 -> {
                        navHostController.navigate(DryFlowerScreen.Artist(term))
                    }
                    2 -> {
                        navHostController.navigate(DryFlowerScreen.Album(term))
                    }
                }
            }
        )
    }
    composable<DryFlowerScreen.Album> {
        AlbumScreen(onShowSnackbar)
    }
    composable<DryFlowerScreen.Artist> {
        ArtistScreen()
    }
}