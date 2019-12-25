package com.example.randommovie.data.vo.models

import com.google.gson.annotations.SerializedName

data class GenreModel(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)