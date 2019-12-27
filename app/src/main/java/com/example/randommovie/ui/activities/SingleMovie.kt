package com.example.randommovie.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.data.api.POSTER_BASE_URL
import com.example.randommovie.data.api.TheMovieDBClient
import com.example.randommovie.data.api.TheMovieDBInterface
import com.example.randommovie.data.localRepository.MovieDetailsRepository
import com.example.randommovie.data.repository.NetworkState
import com.example.randommovie.data.repository.firebase.MoviesRepository
import com.example.randommovie.data.viewmodel.SingleMovieViewModel
import com.example.randommovie.data.vo.MovieDetails
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    private lateinit var BookmarkButton : MaterialButton
    private lateinit var FavouriteButton : MaterialButton
    private lateinit var ViewedButton : MaterialButton

    private lateinit var movies : MoviesRepository

    private var movieId : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        initButtons()
        onClicks()

        movies = MoviesRepository(this)

        movieId = intent.getIntExtra("id",1)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository =
            MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

    }

    private fun initButtons(){
        BookmarkButton = single_bookmark_button
        FavouriteButton = single_favourite_button
        ViewedButton = single_shown_button
    }

    private fun onClicks(){
        BookmarkButton.setOnClickListener {
            movies.addBookmark(movieId)
        }

        FavouriteButton.setOnClickListener {
            movies.addFavourite(movieId)
        }

        ViewedButton.setOnClickListener {
            movies.addViewed(movieId)
        }
    }

    private fun bindUI(it: MovieDetails){
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);

    }


    private fun getViewModel(movieId:Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(
                    movieRepository,
                    movieId
                ) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}
