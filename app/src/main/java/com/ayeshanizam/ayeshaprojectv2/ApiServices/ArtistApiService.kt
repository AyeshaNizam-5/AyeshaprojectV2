package com.ayeshanizam.ayeshaprojectv2.ApiServices

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface ArtistApiService {
        @GET("/2.0/")
        fun getTopArtists(
            @Query("method") method: String = "chart.gettopartists",
            @Query("api_key") apiKey: String,
            @Query("format") format: String = "json"
        ): Call<ArtistsResponse>

}