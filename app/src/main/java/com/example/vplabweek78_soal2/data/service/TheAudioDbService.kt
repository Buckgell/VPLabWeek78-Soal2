package com.example.vplabweek78_soal2.data.service

import com.example.vplabweek78_soal2.data.dto.AlbumDetailResponse
import com.example.vplabweek78_soal2.data.dto.AlbumTracksResponse
import com.example.vplabweek78_soal2.data.dto.ArtistAlbumsResponse
import com.example.vplabweek78_soal2.data.dto.ArtistResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheAudioDbService
{
    @GET("search.php")
    suspend fun searchArtist(
        @Query("s") artistName: String
    ): ArtistResponse

    @GET("searchalbum.php")
    suspend fun getArtistAlbums(
        @Query("s") artistName: String
    ): ArtistAlbumsResponse

    @GET("album.php")
    suspend fun getAlbumDetails(
        @Query("m") albumId: String
    ): AlbumDetailResponse

    @GET("track.php")
    suspend fun getAlbumTracks(
        @Query("m") albumId: String
    ): AlbumTracksResponse
}