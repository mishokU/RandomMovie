package com.example.randommovie.data.repository.firebase

import android.content.ContentValues.TAG
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.randommovie.activities.MainTapeActivivty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.randommovie.ui.utils.launchActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class Authentication(private var context: Context?) {

    private var instance : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var registration : DatabaseReference
    private var auth = FirebaseAuth.getInstance()

    private lateinit var mProgressBar : ProgressBar

    init{
        registration = instance.reference.child("users")
    }

    fun setProgressBar(progressBar : ProgressBar){
        mProgressBar = progressBar
    }

    fun login(email : String, password : String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                   mProgressBar.visibility = View.GONE
                   context?.launchActivity<MainTapeActivivty>()
                }
                else
                {
                   Toast.makeText(context,"Login failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun registration(email : String, password : String, repeat_password : String, login : String){
        if (!TextUtils.isEmpty(repeat_password) && !TextUtils.isEmpty(login)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && (repeat_password == password)) {
            auth.createUserWithEmailAndPassword(email,password)
            mProgressBar.visibility = View.GONE
            Log.d(TAG, "createUserWithEmail:success")
            val userId = auth.currentUser!!.uid
            //update user profile information
            registration.child(userId)

            registration.child("email").setValue(email)
            registration.child("login").setValue(login)
            registration.child("password").setValue(password)

            context?.launchActivity<MainTapeActivivty>()

        } else {
            mProgressBar.visibility = View.GONE
            Toast.makeText(context, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }


}