package com.example.randommovie.data.vo.models

data class BookmarkModel(
    val id : String ?= "",
    val title : String ?= "",
    val description : String ?= "",
    val rating : String ?= "",
    val image_url : String ?= "",
    val release_data : String ?= ""
)