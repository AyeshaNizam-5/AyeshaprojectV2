package com.ayeshanizam.ayeshaprojectv2.ApiServices
// Artist.kt
data class Artist(
    val name: String,
    val playcount: Long,
    val listeners: Long,
    val mbid: String,
    val url: String,
    val streamable: Long,
    val image: List<Image>
)

data class Image(
    val size: String,
    val url: String
)

// ArtistsResponse.kt
data class ArtistsResponse(
    val artists: Artists
)

data class Artists(
    val artist: List<Artist>,
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val total: Int
)
