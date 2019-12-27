package com.example.randommovie.ui.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.example.randommovie.adapters.ViewPagerAdapter

import com.example.randommovie.R
import com.example.randommovie.data.repository.firebase.Authentication
import com.example.randommovie.ui.fragments.FragmentLogIn
import com.example.randommovie.ui.fragments.FragmentRegistration
import com.example.randommovie.ui.utils.launchActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_start.*

class MainActivity : AppCompatActivity(),FragmentLogIn.OnLogInInterface,FragmentRegistration.OnRegistrationInterface {

    private var viewPager : ViewPager ?= null
    private var viewPagerAdapter : ViewPagerAdapter ?= null

    private var auth : Authentication ?= null
    private var mProgressBar : ProgressBar ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        initVariables()
        initViewPagerAdapter()
    }

    private fun initVariables() {
        //tabLayout = main_tabLayout
        viewPager = main_ViewPager
        mProgressBar = login_progress_bar
        auth = Authentication(this)
        auth!!.setProgressBar(mProgressBar)
    }

    private fun initViewPagerAdapter(){
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter!!.addFragment(FragmentLogIn(),"Login")
        viewPagerAdapter!!.addFragment(FragmentRegistration(), "Registration")
        viewPager!!.adapter = viewPagerAdapter

        //tabLayout?.setupWithViewPager(viewPager)
    }

    override fun onLogIn(email: String, password: String){
        mProgressBar?.visibility = View.VISIBLE
        auth?.login(email, password)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }

    override fun onRegistration(
        email: String,
        password: String,
        repeat_password: String,
        login: String
    ) {
        mProgressBar?.visibility = View.VISIBLE
        auth?.registration(email,password,repeat_password,login)
    }

    override fun goToRegistration() {
        viewPager?.currentItem = 1
    }

    override fun goToLogIn() {
        viewPager?.currentItem = 0
    }

}
