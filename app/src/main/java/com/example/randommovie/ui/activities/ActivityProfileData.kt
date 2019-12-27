package com.example.randommovie.ui.activities

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.randommovie.R
import com.example.randommovie.data.repository.firebase.Authentication
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_profile_data.*

class ActivityProfileData : AppCompatActivity() {

    private lateinit var toolbar : Toolbar
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var login : EditText
    private lateinit var updateData : MaterialButton

    private lateinit var auth : Authentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_data)

        initFirebase()

        init()
        initToolbar()
        profileData()
        updateData()
    }

    fun init(){
        toolbar = profile_data_toolbar
        email = profile_data_email
        password = profile_data_password
        login  = profile_data_login
        updateData  = profile_data_update_button
    }

    private fun initFirebase(){
        auth = Authentication(this)
        auth.setProgressBar(profile_data_progress_bar)
    }

    private fun initToolbar(){
        setSupportActionBar(toolbar)
        if(supportActionBar != null){
            toolbar.title = "Profile Data"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun profileData(){
        auth.getProfileData(email,password,login)
    }

    private fun updateData(){
        updateData.setOnClickListener {
                auth.updateData(email.text.toString(), password.text.toString(), login.text.toString())
            }
    }
}