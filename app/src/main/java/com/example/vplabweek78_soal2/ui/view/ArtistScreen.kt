package com.example.vplabweek78_soal2.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vplabweek78_soal2.ui.viewmodel.ArtistViewModel

@Composable
fun ArtistScreen(
    viewModel: ArtistViewModel,
    artistName: String,
    onAlbumClick: (String) -> Unit
)
{
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = artistName)
    {
        viewModel.loadArtist(artistName)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    )
    {
        when
        {
            uiState.isLoading -> LoadingState()
            uiState.error != null -> ErrorState(message = uiState.error!!)
            uiState.artist != null ->
                {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                )
                {
                    Text(
                        text = uiState.artist!!.strArtist,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 48.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(Modifier.height(24.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .aspectRatio(1f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                    {
                        Box {
                            AsyncImage(
                                model = uiState.artist?.strArtistThumb,
                                contentDescription = "Artist Photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .fillMaxWidth()
                                    .background(
                                        androidx.compose.ui.graphics.Brush.verticalGradient(
                                            colors = listOf(
                                                androidx.compose.ui.graphics.Color.Transparent,
                                                androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.7f)
                                            )
                                        )
                                    )
                                    .padding(16.dp)
                            )
                            {
                                Text(
                                    text = uiState.artist!!.strArtist,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = androidx.compose.ui.graphics.Color.White
                                )
                                Text(
                                    text = uiState.artist!!.strGenre,
                                    fontSize = 14.sp,
                                    color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Column(modifier = Modifier.padding(horizontal = 16.dp))
                    {
                        Text(
                            "Albums",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(Modifier.height(12.dp))

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 1000.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        )
                        {
                            items(uiState.albums) { album ->
                                AlbumGridItem(
                                    album = album,
                                    genre = uiState.artist?.strGenre ?: "",
                                    onClick = { onAlbumClick(album.idAlbum) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AlbumGridItem(
    album: com.example.vplabweek78_soal2.data.dto.AlbumSimpleDto,
    genre: String,
    onClick: () -> Unit
)
{
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
    {
        Column {
            AsyncImage(
                model = album.strAlbumThumb,
                contentDescription = album.strAlbum,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            )
            {
                Text(
                    text = album.strAlbum,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${album.intYearReleased} • $genre",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}