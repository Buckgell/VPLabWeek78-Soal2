package com.example.vplabweek78_soal2.data.repository

import com.example.vplabweek78_soal2.data.dto.AlbumDetailDto
import com.example.vplabweek78_soal2.data.dto.AlbumSimpleDto
import com.example.vplabweek78_soal2.data.dto.ArtistDto
import com.example.vplabweek78_soal2.data.dto.TrackDto
import com.example.vplabweek78_soal2.data.service.TheAudioDbService

class ArtistRepository(private val apiService: TheAudioDbService)
{
    private suspend fun <T> safeApiCall(call: suspend () -> T): Result<T>
    {
        return try
        {
            Result.success(call())
        }
        catch (e: Exception)
        {
            Result.failure(e)
        }
    }

    suspend fun getArtistDetails(name: String): Result<ArtistDto> = safeApiCall {
        apiService.searchArtist(name).artists?.firstOrNull()
            ?: throw Exception("Artist not found")
    }

    suspend fun getArtistAlbums(name: String): Result<List<AlbumSimpleDto>> = safeApiCall {
        apiService.getArtistAlbums(name).album ?: emptyList()
    }

    suspend fun getAlbumDetails(id: String): Result<AlbumDetailDto> = safeApiCall {
        apiService.getAlbumDetails(id).album?.firstOrNull()
            ?: throw Exception("Album details not found")
    }

    suspend fun getAlbumTracks(id: String): Result<List<TrackDto>> = safeApiCall {
        apiService.getAlbumTracks(id).track ?: emptyList()
    }
}