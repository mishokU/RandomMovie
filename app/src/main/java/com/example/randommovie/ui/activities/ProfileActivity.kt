package com.example.randommovie.activities

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.randommovie.R
import com.example.randommovie.ui.activities.*
import com.example.randommovie.ui.utils.launchActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile_info.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var mNavigation : NavigationView
    private lateinit var mToolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info)
        init()
        initToolbar()
        initNavigation()
    }

    private fun init(){
        mNavigation = navigation_view
        mToolbar = profile_toolbar
    }

    private fun initToolbar(){
        setSupportActionBar(mToolbar)
        supportActionBar?.title = "My Profile"
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initNavigation(){
        mNavigation.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.profile_data->{
                    launchActivity<ActivityProfileData>()
                    true
                }
                R.id.bookmark->{
                    launchActivity<ActivityProfilesBookmarks>()
                    true
                }
                R.id.favourite->{
                    launchActivity<ActivityProfileFavourites>()
                    true
                }
                R.id.shown->{
                    launchActivity<ActivityProfileShownFilms>()
                    true
                }
                R.id.exit->{
                    createExitAlertDialog()
                    true
                }
                else->false
            }
        }
    }

    private fun createExitAlertDialog(){
        val exitDialog = AlertDialog.Builder(this)
        exitDialog.setTitle("Log out")
            .setMessage("Login information will not be saved.")
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Log out") { _, _ ->
                run {
                    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
                    firebaseAuth.signOut()
                    launchActivity<MainActivity>()
                }
            }.show()
    }

}