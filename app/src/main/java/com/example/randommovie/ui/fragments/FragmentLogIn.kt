package com.example.randommovie.ui.fragments

import android.content.Context
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

    private var mLogInInt : OnLogInInterface ?= null

    interface OnLogInInterface{
        fun onLogIn(email : String, password : String)
    }

    private lateinit var mLoginButton : MaterialButton
    private lateinit var mEmail : EditText
    private lateinit var mPassword : EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.activity_log_in, container, false)

        mLoginButton = view.login_button_log_in
        mEmail = view.email_log_in
        mPassword = view.password_log_in

        login()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnLogInInterface){
           mLogInInt = context
        }
    }

    private fun login(){
        mLoginButton.setOnClickListener {
            mLogInInt?.onLogIn(mEmail.text.toString(), mPassword.text.toString())
        }
    }

}