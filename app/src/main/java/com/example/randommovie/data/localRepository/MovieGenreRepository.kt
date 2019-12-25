package com.example.randommovie.data.localRepository

import androidx.lifecycle.LiveData
import com.example.randommovie.data.api.TheMovieDBInterface
import com.example.randommovie.data.repository.NetworkState
import com.example.randommovie.data.repository.genre.GenreDetailsNetworkDataSource
import com.example.randommovie.data.vo.GenreResponse
import io.reactivex.disposables.CompositeDisposable

class MovieGenreRepository(private val apiService : TheMovieDBInterface) {

    lateinit var genreDetailsNetworkDataSource: GenreDetailsNetworkDataSource

    fun fetchGenre (compositeDisposable: CompositeDisposable, lang : String) : LiveData<GenreResponse> {

        genreDetailsNetworkDataSource = GenreDetailsNetworkDataSource(apiService,compositeDisposable)
        genreDetailsNetworkDataSource.fetchGenreDetails(lang)

        return genreDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return genreDetailsNetworkDataSource.networkState
    }
}