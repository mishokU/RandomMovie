package com.example.randommovie.data.vo


import com.google.gson.annotations.SerializedName
import com.example.randommovie.data.vo.models.Movie

data class MovieResponse(
    val page: Int,
    @SerializedName("results")
    val movieList: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)