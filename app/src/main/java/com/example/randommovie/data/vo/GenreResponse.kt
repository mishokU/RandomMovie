package com.example.randommovie.data.vo

import com.example.randommovie.data.vo.models.GenreModel
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres") val genreList : ArrayList<GenreModel>
)