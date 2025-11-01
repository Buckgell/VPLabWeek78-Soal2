package com.example.vplabweek78_soal2.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vplabweek78_soal2.ui.viewmodel.AlbumDetailViewModel

@Composable
fun AlbumDetailScreen(
    viewModel: AlbumDetailViewModel,
    albumId: String
)
{
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = albumId)
    {
        viewModel.loadAlbumDetails(albumId)
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
            uiState.album != null ->
                {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                )
                {
                    item {
                        Text(
                            text = uiState.album!!.strAlbum,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 48.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(Modifier.height(24.dp))
                    }

                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                        {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                )
                                {
                                    AsyncImage(
                                        model = uiState.album?.strAlbumThumb,
                                        contentDescription = "Album Cover",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )

                                    Column(
                                        modifier = Modifier
                                            .align(androidx.compose.ui.Alignment.BottomStart)
                                            .fillMaxWidth()
                                            .background(
                                                androidx.compose.ui.graphics.Brush.verticalGradient(
                                                    colors = listOf(
                                                        androidx.compose.ui.graphics.Color.Transparent,
                                                        androidx.compose.ui.graphics.Color.Black.copy(
                                                            alpha = 0.7f
                                                        )
                                                    )
                                                )
                                            )
                                            .padding(16.dp)
                                    )
                                    {
                                        Text(
                                            text = uiState.album!!.strAlbum,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = androidx.compose.ui.graphics.Color.White
                                        )
                                        Text(
                                            text = "${uiState.album!!.intYearReleased} • ${uiState.album!!.strGenre}",
                                            fontSize = 14.sp,
                                            color = androidx.compose.ui.graphics.Color.White.copy(
                                                alpha = 0.8f
                                            )
                                        )
                                    }
                                }

                                val description = uiState.album!!.strDescriptionEN
                                if (!description.isNullOrBlank())
                                {
                                    Text(
                                        text = description,
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(24.dp))
                    }

                    item {
                        Text(
                            "Tracks",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                    }

                    itemsIndexed(uiState.tracks) { index, track ->
                        TrackListItem(track = track, trackNumber = index + 1)
                        if (index < uiState.tracks.size - 1)
                        {
                            Divider(modifier = Modifier.padding(horizontal = 24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrackListItem(track: com.example.vplabweek78_soal2.data.dto.TrackDto, trackNumber: Int)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(
            text = "$trackNumber. ${track.strTrack}",
            modifier = Modifier.weight(1f)
        )
        val durationSeconds = (track.intDuration.toIntOrNull() ?: 0) / 1000
        val minutes = durationSeconds / 60
        val seconds = durationSeconds % 60
        Text(text = "$minutes:${String.format("%02d", seconds)}")
    }
}