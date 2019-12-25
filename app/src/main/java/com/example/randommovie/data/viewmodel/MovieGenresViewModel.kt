package com.example.randommovie.data.viewmodel

import androidx.collection.ArraySet
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.randommovie.data.localRepository.MoviePagedListRepository
import com.example.randommovie.data.repository.NetworkState
import com.example.randommovie.data.vo.models.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieGenresViewModel(
    private val movieRepository: MoviePagedListRepository,
    genresString: ArraySet<Int>
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchGenresLiveMoviePagedList(compositeDisposable,genresString)
    }


    val  networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}