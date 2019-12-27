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
import kotlinx.android.synthetic.main.activity_registration.view.*

class FragmentRegistration : Fragment() {

    private var mRegistrationInt : OnRegistrationInterface ?= null

    interface OnRegistrationInterface{
        fun onRegistration(email : String, password : String,
                           repeat_password : String, login : String)
    }

    private lateinit var mEmail : EditText
    private lateinit var mPassword : EditText
    private lateinit var mRepeatPassword : EditText
    private lateinit var mUserName : EditText
    private lateinit var mRegistration : MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.activity_registration,container,false)

        mEmail = view.email_sign_up
        mPassword = view.password_sign_up
        mRepeatPassword = view.repeat_password_sign_up
        mUserName = view.username_sign_up
        mRegistration = view.registration_sign_up

        registration()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnRegistrationInterface){
           mRegistrationInt = context
        }
    }



    private fun registration(){
        mRegistration.setOnClickListener {
            mRegistrationInt?.onRegistration(
                mEmail.text.toString(),
                mPassword.text.toString(),
                mRepeatPassword.text.toString(),
                mUserName.text.toString())
        }
    }
}