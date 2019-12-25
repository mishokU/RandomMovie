package com.example.randommovie.data.repository.genre

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.randommovie.data.api.FIRST_PAGE
import com.example.randommovie.data.api.TheMovieDBInterface
import com.example.randommovie.data.repository.NetworkState
import com.example.randommovie.data.vo.GenreResponse
import com.example.randommovie.data.vo.models.GenreModel
import com.example.randommovie.data.vo.models.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GenreDataSource (apiService : TheMovieDBInterface, compositeDisposable: CompositeDisposable) {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    init{
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getAllGenres("en-US")
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        networkState.postValue(NetworkState.LOADED)
                        Log.e("MovieDataSource", it.genreList.toString())
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message)
                    }
                )
        )
    }

}