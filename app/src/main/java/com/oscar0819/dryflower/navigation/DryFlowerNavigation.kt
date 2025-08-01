package com.oscar0819.dryflower.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.oscar0819.core.artist.ArtistScreen
import com.oscar0819.feature.album.AlbumScreen
import com.oscar0819.feature.search.SearchScreen

fun NavGraphBuilder.dryflowerNavigation(navHostController: NavHostController) {
    composable<DryFlowerScreen.Search> {
        SearchScreen(
            onNavigateToAlbums = { term ->
                navHostController.navigate(DryFlowerScreen.Album(term))
            }
        )
    }
    composable<DryFlowerScreen.Album> {
        AlbumScreen()
    }
    composable<DryFlowerScreen.Artist> {
        ArtistScreen()
    }
}