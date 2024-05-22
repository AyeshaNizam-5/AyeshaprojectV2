package com.ayeshanizam.ayeshaprojectv2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//STEP1
object ApiClient {

    private const val BASE_URL = "https://ws.audioscrobbler.com/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}