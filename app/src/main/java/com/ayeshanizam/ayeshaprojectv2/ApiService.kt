package com.ayeshanizam.ayeshaprojectv2



import com.ayeshanizam.ayeshaprojectv2.LastFmResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("2.0/")
    fun searchTracks(
        @Query("method") method: String = "track.search",
        @Query("track") track: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String = "json"
    ): Call<LastFmResponse>

}