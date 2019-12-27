package com.example.randommovie.holders


import androidx.collection.ArraySet
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.randommovie.data.api.TheMovieDBInterface
import com.example.randommovie.data.repository.MovieDataSource
import com.example.randommovie.data.repository.movies.GenreMovieDataSource

import com.example.randommovie.data.vo.models.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()
    val genreMoviesLiveDataSource = MutableLiveData<GenreMovieDataSource>()

    private var genresString = ArraySet<Int>()

    fun GenresString(genresString : ArraySet<Int>){
        this.genresString = genresString
    }

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource : MovieDataSource
        val genreMovieDataSource : GenreMovieDataSource

        return if(genresString.count() > 0){
            genreMovieDataSource = GenreMovieDataSource(apiService,compositeDisposable, genresString)
            genreMoviesLiveDataSource.postValue(genreMovieDataSource)
            println("genres")
            genreMovieDataSource
        }else {
            movieDataSource = MovieDataSource(apiService,compositeDisposable)
            moviesLiveDataSource.postValue(movieDataSource)
            println("popular")
            movieDataSource
        }
    }

    fun clearMovies() {
        create()
    }
}