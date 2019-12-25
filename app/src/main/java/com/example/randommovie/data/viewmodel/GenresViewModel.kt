package com.example.randommovie.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.randommovie.data.localRepository.MovieGenreRepository
import com.example.randommovie.data.repository.NetworkState
import com.example.randommovie.data.vo.GenreResponse
import com.example.randommovie.data.vo.models.GenreModel
import io.reactivex.disposables.CompositeDisposable

class GenresViewModel(private val movieGenreRepository : MovieGenreRepository, lang : String) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val genreDetails : LiveData<GenreResponse> by lazy {
        movieGenreRepository.fetchGenre(compositeDisposable, lang)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieGenreRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}