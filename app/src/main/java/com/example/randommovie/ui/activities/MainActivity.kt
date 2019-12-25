package com.example.randommovie.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.viewpager.widget.ViewPager
import com.example.randommovie.adapters.ViewPagerAdapter

import com.example.randommovie.R
import com.example.randommovie.ui.fragments.FragmentLogIn
import com.example.randommovie.ui.fragments.FragmentRegistration
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_start.*

class MainActivity : AppCompatActivity() {

    var tabLayout : TabLayout ?= null
    var viewPager : ViewPager ?= null
    var viewPagerAdapter : ViewPagerAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        initVariables()
        initViewPagerAdapter()
    }

    private fun initVariables() {
        tabLayout = main_tabLayout
        viewPager = main_ViewPager

    }

    private fun initViewPagerAdapter(){
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter!!.addFragment(FragmentLogIn(),"Login")
        viewPagerAdapter!!.addFragment(FragmentRegistration(), "Registration")


        viewPager!!.adapter = viewPagerAdapter

        tabLayout?.setupWithViewPager(viewPager)
    }

}
