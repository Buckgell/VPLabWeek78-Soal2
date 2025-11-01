package com.example.vplabweek78_soal2.data.container

import com.example.vplabweek78_soal2.data.repository.ArtistRepository
import com.example.vplabweek78_soal2.data.service.TheAudioDbService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer
{
    companion object
    {
        private const val BASE_URL = "https://www.theaudiodb.com/api/v1/json/123/"
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: TheAudioDbService by lazy {
        retrofit.create(TheAudioDbService::class.java)
    }

    val artistRepository: ArtistRepository by lazy {
        ArtistRepository(retrofitService)
    }
}