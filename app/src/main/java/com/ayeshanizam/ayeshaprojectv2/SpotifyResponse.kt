package com.ayeshanizam.ayeshaprojectv2

import com.google.gson.annotations.SerializedName

data class LastFmResponse(
    @SerializedName("results") val results: Results
)

data class Results(
    @SerializedName("trackmatches") val trackmatches: TrackMatches
)

data class TrackMatches(
    @SerializedName("track") val track: List<Track>
)

data class Track(
    @SerializedName("name") val name: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("url") val url: String,
    @SerializedName("listeners") val listeners: Int
)