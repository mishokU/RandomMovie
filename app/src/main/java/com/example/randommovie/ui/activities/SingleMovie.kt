package com.example.randommovie.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageButton
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
import com.example.randommovie.data.vo.models.BookmarkModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.lang.StringBuilder
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    private lateinit var BookmarkButton : AppCompatImageButton
    private lateinit var FavouriteButton : AppCompatImageButton
    private lateinit var ViewedButton : AppCompatImageButton
    private lateinit var BlockedButton : AppCompatImageButton

    private lateinit var ratingBar : RatingBar

    private lateinit var movies : MoviesRepository
    private lateinit var movieModel : BookmarkModel

    private var movieId : Int = 1
    private lateinit var title : String

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

        ratingBar = rating_starts
        ratingBar.isEnabled = false
    }

    private fun onClicks(){
        BookmarkButton.setOnClickListener {
            createSnackBar("bookmark")
            movies.addBookmark(movieModel)
        }

        FavouriteButton.setOnClickListener {
            createSnackBar("favourite")
            movies.addFavourite(movieModel)
        }

        ViewedButton.setOnClickListener {
            createSnackBar("seen")
            movies.addViewed(movieModel)
        }
    }

    private fun bindUI(it: MovieDetails){
        movieModel = BookmarkModel(
            it.id.toString(),
            it.title,
            it.overview,
            it.rating.toString(),
            POSTER_BASE_URL + it.posterPath,
            it.releaseDate)

        movie_title.text = it.title
        title = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val genres = StringBuilder()

        for(genre in  it.genreList){
            genres.append(genre.name)
            genres.append(",")
        }

        genres.deleteCharAt(genres.count() - 1)
        genres_view.text = genres


        ratingBar.rating = it.rating.toFloat()

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)
    }

    private fun createSnackBar(s: String) {
        val snackbar : Snackbar = Snackbar.make(coordinator, "$title is added to $s",Snackbar.LENGTH_LONG)
        snackbar.setAction("UNDO") {
            when (s) {
                "seen" -> movies.deleteViewed(movieModel)
                "bookmark" -> movies.deleteBookmark(movieModel)
                "favourite" -> movies.deleteFavourite(movieModel)
            }
            Snackbar.make(coordinator,"$title deleted from $s",Snackbar.LENGTH_SHORT).show()
        }.show()
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
