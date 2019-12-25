package com.example.randommovie.data.repository.movies

import android.util.Log
import androidx.collection.ArraySet
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.randommovie.data.api.FIRST_PAGE
import com.example.randommovie.data.api.TheMovieDBInterface
import com.example.randommovie.data.repository.NetworkState
import com.example.randommovie.data.vo.models.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GenreMovieDataSource (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable,
                            private val genreString : ArraySet<Int>
)
    : PageKeyedDataSource<Int, Movie>(){

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        val tmpList = ArrayList<Movie>()
                        for(movie in it.movieList){
                            if(containAllGenres(movie)){
                                tmpList.add(movie)
                            }
                        }
                        callback.onResult(tmpList, null, page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message)
                    }
                )
        )


    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key) {
                            callback.onResult(it.movieList, params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }
                        else{
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    private fun containAllGenres(movie: Movie): Boolean {
        for(genre in movie.genres){
            for(clickedGenre in genreString){
                if(genre == clickedGenre){
                    return true
                }
            }
        }
        return false
    }
}