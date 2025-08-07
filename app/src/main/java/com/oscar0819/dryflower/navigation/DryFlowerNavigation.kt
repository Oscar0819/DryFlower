package com.oscar0819.dryflower.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.oscar0819.core.artist.ArtistScreen
import com.oscar0819.feature.album.AlbumScreen
import com.oscar0819.feature.search.SearchDetailScreen
import com.oscar0819.feature.search.SearchScreen

fun NavGraphBuilder.dryflowerNavigation(
    navHostController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Unit,
) {
    composable<DryFlowerScreen.Search> {
        SearchScreen(
            onNavigateToSearchDetail = {
                navHostController.navigate(DryFlowerScreen.SearchDetail)
            }
        )
    }
    composable<DryFlowerScreen.SearchDetail> {
        SearchDetailScreen(
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