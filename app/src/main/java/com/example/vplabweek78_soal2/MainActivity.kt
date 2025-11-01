package com.example.vplabweek78_soal2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vplabweek78_soal2.data.container.AppContainer
import com.example.vplabweek78_soal2.ui.theme.Vplabweek78_soal2Theme
import com.example.vplabweek78_soal2.ui.view.AlbumDetailScreen
import com.example.vplabweek78_soal2.ui.view.ArtistScreen
import com.example.vplabweek78_soal2.ui.viewmodel.AlbumDetailViewModel
import com.example.vplabweek78_soal2.ui.viewmodel.ArtistViewModel

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Vplabweek78_soal2Theme {
                ArtistApp()
            }
        }
    }
}

@Composable
fun ArtistApp()
{
    val appContainer = remember { AppContainer() }

    val navController = rememberNavController()

    val viewModelFactory = remember {
        object : ViewModelProvider.Factory
        {
            override fun <T : ViewModel> create(modelClass: Class<T>): T
            {
                return when
                {
                    modelClass.isAssignableFrom(ArtistViewModel::class.java) ->
                        {
                        ArtistViewModel(appContainer.artistRepository) as T
                        }
                    modelClass.isAssignableFrom(AlbumDetailViewModel::class.java) ->
                        {
                        AlbumDetailViewModel(appContainer.artistRepository) as T
                        }
                    else -> throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = "artist/{artistName}")
    {
        composable(
            route = "artist/{artistName}",
            arguments = listOf(navArgument("artistName")
            {
                type = NavType.StringType
                defaultValue = "John Mayer"
            })
        )
        { backStackEntry ->
            val artistName = backStackEntry.arguments?.getString("artistName")!!
            val artistViewModel: ArtistViewModel = viewModel(factory = viewModelFactory)

            ArtistScreen(
                viewModel = artistViewModel,
                artistName = artistName,
                onAlbumClick = { albumId ->
                    navController.navigate("album/$albumId")
                }
            )
        }
        composable(
            route = "album/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.StringType })
        )
        { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")!!
            val albumViewModel: AlbumDetailViewModel = viewModel(factory = viewModelFactory)

            AlbumDetailScreen(
                viewModel = albumViewModel,
                albumId = albumId
            )
        }
    }
}