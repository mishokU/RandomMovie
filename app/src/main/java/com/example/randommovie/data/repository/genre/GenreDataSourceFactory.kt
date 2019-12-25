package com.example.randommovie.data.repository.genre

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.randommovie.data.api.TheMovieDBInterface
import com.example.randommovie.data.repository.MovieDataSource
import com.example.randommovie.data.vo.models.GenreModel
import com.example.randommovie.data.vo.models.Movie
import io.reactivex.disposables.CompositeDisposable

class GenreDataSourceFactory (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) {

    val genresLiveDataSource =  MutableLiveData<GenreDataSource>()

    fun create(): GenreDataSource {
        val genreDataSource = GenreDataSource(apiService,compositeDisposable)

        genresLiveDataSource.postValue(genreDataSource)
        return genreDataSource
    }


}