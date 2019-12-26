package com.example.randommovie.data.repository.firebase

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.randommovie.activities.MainTapeActivivty
import com.example.randommovie.data.vo.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.example.randommovie.ui.utils.launchActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class Authentication(private var activity: Activity) {

    private var instance : FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var userRef : DatabaseReference
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var userId : String

    private lateinit var mProgressBar : ProgressBar

    fun setProgressBar(progressBar : ProgressBar){
        mProgressBar = progressBar
    }

    fun login(email : String, password : String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                   mProgressBar.visibility = View.GONE
                   activity.applicationContext?.launchActivity<MainTapeActivivty>()
                   activity.finish()
                }
                else
                {
                   Toast.makeText(activity.applicationContext,"Login failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun registration(email : String, password : String, repeat_password : String, login : String){
        if (!TextUtils.isEmpty(repeat_password) && !TextUtils.isEmpty(login)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && (repeat_password == password)) {
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        mProgressBar.visibility = View.GONE

                        userId = auth.currentUser!!.uid
                        userRef = instance.reference.child("users").child(userId).child("data")
                        userRef.child("email").setValue(email)
                        userRef.child("login").setValue(login)
                        userRef.child("password").setValue(password)

                        activity.applicationContext?.launchActivity<MainTapeActivivty>()
                        activity.finish()
                    }
                    else{
                        Log.e("log","bad auth")
                        mProgressBar.visibility = View.GONE
                    }
                }
        } else {
            mProgressBar.visibility = View.GONE
            Toast.makeText(activity.applicationContext, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateData(email: String, password: String, login: String) {
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(login)){
            userId = auth.currentUser!!.uid
            userRef = instance.reference.child("users").child(userId).child("data")
            userRef.child("email").setValue(email)
            userRef.child("login").setValue(login)
            userRef.child("password").setValue(password)
        }
    }

    fun getProfileData(email : EditText, password : EditText, login : EditText) {
        userId = auth.currentUser!!.uid
        userRef = instance.reference.child("users").child(userId).child("data")
        userRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("loadError","User data is not loaded")
            }

            override fun onDataChange(userModel: DataSnapshot) {
                val user= userModel.getValue(UserModel::class.java)
                if (user != null) {
                    email.setText(user.email)
                    password.setText(user.password)
                    login.setText(user.login)
                }
            }
        })
    }

    fun inSystem() : Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }
}