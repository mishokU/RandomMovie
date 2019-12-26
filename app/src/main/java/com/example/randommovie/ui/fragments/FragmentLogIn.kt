package com.example.randommovie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.randommovie.R
import com.example.randommovie.data.repository.firebase.Authentication
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_log_in.view.*

class FragmentLogIn : Fragment() {

    private lateinit var mLoginButton : MaterialButton
    private lateinit var mEmail : EditText
    private lateinit var mPassword : EditText
    private lateinit var mProgressBar : ProgressBar

    private lateinit var auth : Authentication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.activity_log_in, container, false)

        mLoginButton = view.login_button_log_in
        mEmail = view.email_log_in
        mPassword = view.password_log_in
        mProgressBar = view.login_progress_bar

        auth = Authentication()

        login()
        return view
    }


    private fun login(){
        auth.setProgressBar(mProgressBar)
        mLoginButton.setOnClickListener {
            mProgressBar.visibility = View.VISIBLE
            auth.login(mEmail.text.toString(), mPassword.text.toString())
        }
    }

}