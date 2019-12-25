package com.example.randommovie.data.api

import com.example.randommovie.data.vo.GenreResponse
import com.example.randommovie.data.vo.MovieDetails
import com.example.randommovie.data.vo.MovieResponse
import com.example.randommovie.data.vo.models.GenreModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=6e63c2317fbe963d76c3bdc2b785f6d1
    // https://api.themoviedb.org/3/genre/movie/list?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&language=en-US
    // https://api.themoviedb.org/3/

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("genre/movie/list")
    fun getAllGenres(@Query("language") lang : String) : Single<GenreResponse>

    @GET("movie/popular")
    fun getGenreMovie(@Query("page") page: Int): Single<MovieResponse>
}