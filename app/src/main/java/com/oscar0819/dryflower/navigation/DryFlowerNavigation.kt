package com.oscar0819.dryflower.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.oscar0819.core.artist.ArtistScreen
import com.oscar0819.feature.album.AlbumDetailScreen
import com.oscar0819.feature.album.AlbumScreen
import com.oscar0819.feature.search.SearchDetailScreen
import com.oscar0819.feature.search.SearchScreen

// TODO 더 효율적인 구조 구상하기
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.dryflowerNavigation(
    sharedTransitionScope: SharedTransitionScope,
    navHostController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Unit,
) {
    composable<DryFlowerScreen.Search>{
        sharedTransitionScope.SearchScreen(
            onNavigateToSearchDetail = {
                navHostController.navigate(DryFlowerScreen.SearchDetail)
            },
            animationVisibilityScope = this
        )
    }
    composable<DryFlowerScreen.SearchDetail> {
        sharedTransitionScope.SearchDetailScreen(
            onNavigateToNextScreen = { data, searchType ->
                when (searchType?.id) {
                    1 -> {
                        navHostController.navigate(DryFlowerScreen.Artist(data))
                    }
                    2 -> {
                        navHostController.navigate(DryFlowerScreen.Album(data))
                    }
                    3 -> {
                        val collectionId = data.toIntOrNull() ?: -1
                        navHostController.navigate(DryFlowerScreen.AlbumDetail(collectionId))
                    }
                }
            },
            animationVisibilityScope = this
        )
    }

    composable<DryFlowerScreen.AlbumDetail> {
        AlbumDetailScreen(onShowSnackbar)
    }

    composable<DryFlowerScreen.Album> {
        AlbumScreen(onShowSnackbar)
    }
    composable<DryFlowerScreen.Artist> {
        ArtistScreen()
    }
}