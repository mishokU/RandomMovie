package com.example.randommovie.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.collection.ArraySet
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.randommovie.R
import com.example.randommovie.data.api.TheMovieDBClient
import com.example.randommovie.data.api.TheMovieDBInterface
import com.example.randommovie.data.localRepository.MovieGenreRepository
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_main_tape.*
import kotlinx.android.synthetic.main.bottom_sheet_main_tape.*
import kotlinx.android.synthetic.main.bottom_sheet_main_tape.view.*
import com.example.randommovie.data.localRepository.MoviePagedListRepository
import com.example.randommovie.data.repository.NetworkState
import com.example.randommovie.data.viewmodel.GenresViewModel
import com.example.randommovie.data.viewmodel.MainActivityViewModel
import com.example.randommovie.data.viewmodel.MovieGenresViewModel
import com.example.randommovie.data.vo.GenreResponse
import com.example.randommovie.data.vo.models.GenreModel
import com.example.randommovie.ui.adapters.PopularMoviePagedListAdapter
import com.example.randommovie.ui.utils.launchActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_tape.progress_bar_popular
import kotlinx.android.synthetic.main.activity_main_tape.rv_movie_list
import kotlinx.android.synthetic.main.activity_main_tape.txt_error_popular

class MainTapeActivivty : AppCompatActivity() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>
    private lateinit var toolbar : Toolbar
    private lateinit var chipGroup : ChipGroup

    private lateinit var apiService : TheMovieDBInterface

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var genreViewModel : GenresViewModel

    private lateinit var movieRepository: MoviePagedListRepository
    private lateinit var movieAdapter : PopularMoviePagedListAdapter
    private lateinit var gridLayoutManager : GridLayoutManager

    private lateinit var genreRepository: MovieGenreRepository

    private lateinit var genres : ArrayList<GenreModel>
    private lateinit var genresString : ArraySet<Int>

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tape)

        apiService = TheMovieDBClient.getClient()
        genres = ArrayList()
        genresString = ArraySet()
        auth = FirebaseAuth.getInstance()

        init()
        initAdapter()
        initViewModel()
        getMovies()
        getAllGenres()
    }

    private fun initAdapter(){

        movieRepository = MoviePagedListRepository(apiService)

        viewModel = getViewModel()

        movieAdapter = PopularMoviePagedListAdapter(this)

        gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                return if (viewType == movieAdapter.MOVIE_VIEW_TYPE) 1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        }
    }

    private fun initViewModel(){
        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter
    }

    private fun getMovies(){

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })
    }

    private fun getAllGenres(){
        genreRepository = MovieGenreRepository(apiService)
        genreViewModel = getAllGenresViewModel("en-US")

        genres = ArrayList()

        genreViewModel.genreDetails.observe(this, Observer {
            addChips(it)
        })

        genreViewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })
    }


    private fun init(){

        bottomSheetBehavior = BottomSheetBehavior.from<NestedScrollView>(bottom_sheet)
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(p0: View, p1: Float) {
            }

            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(p0: View, p1: Int) {
                when(p1){
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottom_sheet.show_bottom_sheet.setImageResource(R.drawable.expand_arrow__down_50px)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED->{
                        bottom_sheet.show_bottom_sheet.setImageResource(R.drawable.expand_arrow_50px)
                    }
                }
            }
        })

        bottom_sheet.show_bottom_sheet.setOnClickListener {
            if(BottomSheetBehavior.STATE_EXPANDED == bottomSheetBehavior.state){
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            if(BottomSheetBehavior.STATE_COLLAPSED == bottomSheetBehavior.state){
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        chipGroup = bottom_sheet.chip_group_bottom_sheet

        toolbar = main_tape_toolbar

        setSupportActionBar(toolbar)
        toolbar?.title = "Tape"
    }

    private fun addChips(it : GenreResponse){
        genres = it.genreList
        for(g in it.genreList){
            val chip = Chip(chipGroup.context)
            chip.text= g.name

            // necessary to get single selection working
            chip.isClickable = true
            chip.isCheckable = true
            chip.setOnClickListener {
                getMovieGenres(chip.isChecked,chip.text as String)
            }
            chipGroup.addView(chip)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

    //r
    private fun getMovieGenres(pressed: Boolean, name: String) {
        for(genre in genres){
            if(genre.name == name){
                if(pressed){
                    genresString.add(genre.id)
                }
                else{
                    genresString.remove(genre.id)
                }
            }
        }
        if(genresString.count() == 0){
           getMovies()
        }
        getGenreMovies()
    }

    private fun getGenreMovies(){
        val tmpViewModel = getGenreViewModel(genresString)
        tmpViewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        tmpViewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!tmpViewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_tape_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profile -> {
                launchActivity<ProfileActivity>()
                return true
            }
            R.id.search_bar -> {
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }

    private fun getGenreViewModel(genresString: ArraySet<Int>): MovieGenresViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieGenresViewModel(movieRepository, genresString) as T
            }
        })[MovieGenresViewModel::class.java]
    }

    private fun getAllGenresViewModel(lang : String) : GenresViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return GenresViewModel(genreRepository, lang) as T
            }
        })[GenresViewModel::class.java]
    }
}