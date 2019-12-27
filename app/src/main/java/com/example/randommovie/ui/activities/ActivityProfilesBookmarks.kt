package com.example.randommovie.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import com.example.randommovie.data.viewmodel.SingleMovieViewModel
import com.example.randommovie.data.vo.MovieDetails
import com.example.randommovie.data.vo.MovieResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class ActivityProfilesBookmarks : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel

    private var instance : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        userId = auth.currentUser!!.uid

        initToolbar()
        initFirebaseMoviesIds()
    }

    private fun initToolbar(){

    }

    private fun initFirebaseMoviesIds(){
        val database : DatabaseReference = instance.reference.child("users").child(userId).child("Bookmark")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnaphot: DataSnapshot) {
                for(data in dataSnaphot.children){
                    val id : Long = data.child("id").value as Long
                    println("id$id")
                    initSingleMovieViewModel(id)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    private fun initSingleMovieViewModel(movieId: Long){

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        val movieRepository = MovieDetailsRepository(apiService)

        val viewModel = getViewModel(movieId.toInt(),movieRepository)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        /*viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })*/
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

    private fun getViewModel(movieId:Int, movieRepository : MovieDetailsRepository): SingleMovieViewModel {
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