package com.example.randommovie.data.localRepository

import androidx.collection.ArraySet
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.randommovie.data.api.POST_PER_PAGE
import com.example.randommovie.data.api.TheMovieDBInterface
import com.example.randommovie.data.repository.MovieDataSource
import com.example.randommovie.data.repository.NetworkState
import com.example.randommovie.data.vo.models.Movie
import com.example.randommovie.holders.MovieDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService : TheMovieDBInterface) {

    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun fetchGenresLiveMoviePagedList(compositeDisposable: CompositeDisposable, genresString: ArraySet<Int>) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)
        moviesDataSourceFactory.GenresString(genresString)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()


        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }


    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }

    fun clearMovies() {
        moviesDataSourceFactory.clearMovies()
    }

}