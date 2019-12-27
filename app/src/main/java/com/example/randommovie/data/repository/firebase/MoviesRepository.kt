package com.example.randommovie.data.repository.firebase

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MoviesRepository(private var context: Activity) {

    private var instance : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var userRef : DatabaseReference
    private var auth = FirebaseAuth.getInstance()
    private var userId = auth.currentUser!!.uid

    init{
        userRef = instance.reference.child("users").child(userId)
    }

    fun addBookmark(id : Int) {
        val key: String? = instance.reference.child("users").child(userId).push().key
        if (key != null) {
            userRef.child("Bookmark").child(key).child("id").setValue(id)
            Toast.makeText(context,"Bookmark added", Toast.LENGTH_SHORT).show()
        }
    }

    fun addFavourite(id : Int){
        val key : String? = instance.reference.child("users").child(userId).push().key
        if (key != null) {
            userRef.child("Favourite").child(key).child("id").setValue(id)
        }
    }

    fun addViewed(id : Int){
        val key : String? = instance.reference.child("users").child(userId).push().key
        if (key != null) {
            userRef.child("Viewed").child(key).child("id").setValue(id)
        }
    }



}