package com.example.randommovie.data.vo


import com.example.randommovie.data.vo.models.GenreModel
import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val budget: Int,
    val id: Int,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("genres") val genreList : ArrayList<GenreModel>
)