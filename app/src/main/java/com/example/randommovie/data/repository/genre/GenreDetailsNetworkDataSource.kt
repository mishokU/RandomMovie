package com.example.randommovie.data.repository.genre

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.randommovie.data.api.TheMovieDBInterface
import com.example.randommovie.data.repository.NetworkState
import com.example.randommovie.data.vo.GenreResponse
import com.example.randommovie.data.vo.MovieDetails
import com.example.randommovie.data.vo.models.GenreModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GenreDetailsNetworkDataSource(private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState                   //with this get, no need to implement get function to get networkSate

    private val _downloadedMovieDetailsResponse = MutableLiveData<GenreResponse>()
    val downloadedMovieResponse: LiveData<GenreResponse>
        get() = _downloadedMovieDetailsResponse

    fun fetchGenreDetails(lang : String) {

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getAllGenres(lang)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                            Log.d("genre", it.genreList.toString())
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message)
                        }
                    )
            )


        } catch (e: Exception) {
            Log.e("MovieDetailsDataSource", e.message)
        }

    }
}