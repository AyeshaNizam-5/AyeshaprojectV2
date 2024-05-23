package com.ayeshanizam.ayeshaprojectv2.ApiServices

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object artistApiClient {

    private const val BASE_URL = "http://ws.audioscrobbler.com"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ArtistApiService by lazy {
        retrofit.create(ArtistApiService::class.java)
    }
}