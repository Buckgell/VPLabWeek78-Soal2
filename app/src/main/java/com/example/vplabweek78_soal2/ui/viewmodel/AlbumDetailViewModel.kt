package com.example.vplabweek78_soal2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vplabweek78_soal2.data.dto.AlbumDetailDto
import com.example.vplabweek78_soal2.data.dto.TrackDto
import com.example.vplabweek78_soal2.data.repository.ArtistRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AlbumDetailUiState(
    val isLoading: Boolean = true,
    val album: AlbumDetailDto? = null,
    val tracks: List<TrackDto> = emptyList(),
    val error: String? = null
)

class AlbumDetailViewModel(private val repository: ArtistRepository) : ViewModel()
{
    private val _uiState = MutableStateFlow(AlbumDetailUiState())
    val uiState: StateFlow<AlbumDetailUiState> = _uiState.asStateFlow()

    fun loadAlbumDetails(albumId: String)
    {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val albumDetailsDeferred = async { repository.getAlbumDetails(albumId) }
                val albumTracksDeferred = async { repository.getAlbumTracks(albumId) }

                val albumResult = albumDetailsDeferred.await()
                val tracksResult = albumTracksDeferred.await()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        album = albumResult.getOrThrow(),
                        tracks = tracksResult.getOrThrow()
                    )
                }
            }
            catch (e: Exception)
            {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An error occurred"
                    )
                }
            }
        }
    }
}