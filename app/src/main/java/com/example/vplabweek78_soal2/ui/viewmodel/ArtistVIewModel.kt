package com.example.vplabweek78_soal2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vplabweek78_soal2.data.dto.AlbumSimpleDto
import com.example.vplabweek78_soal2.data.dto.ArtistDto
import com.example.vplabweek78_soal2.data.repository.ArtistRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ArtistUiState(
    val isLoading: Boolean = true,
    val artist: ArtistDto? = null,
    val albums: List<AlbumSimpleDto> = emptyList(),
    val error: String? = null
)

class ArtistViewModel(private val repository: ArtistRepository) : ViewModel()
{
    private val _uiState = MutableStateFlow(ArtistUiState())
    val uiState: StateFlow<ArtistUiState> = _uiState.asStateFlow()

    fun loadArtist(name: String)
    {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val artistDetailsDeferred = async { repository.getArtistDetails(name) }
                val artistAlbumsDeferred = async { repository.getArtistAlbums(name) }

                val artistResult = artistDetailsDeferred.await()
                val albumsResult = artistAlbumsDeferred.await()

                val artist = artistResult.getOrThrow()
                val albums = albumsResult.getOrThrow()

                _uiState.update {
                    it.copy(isLoading = false, artist = artist, albums = albums)
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