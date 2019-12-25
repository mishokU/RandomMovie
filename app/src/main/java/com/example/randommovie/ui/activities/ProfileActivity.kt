package com.example.randommovie.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.randommovie.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var mNavigation : NavigationView
    private lateinit var mToolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
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
        mToolbar.title = "Profile"
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initNavigation(){
        mNavigation.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.profile->{
                    true
                }
                R.id.bookmark->{
                    true
                }
                R.id.favourite->{
                    true
                }
                R.id.shown->{
                    true
                }
                R.id.exit->{
                    true
                }
                else->false
            }
        }
    }


}