package com.example.randommovie.data.repository.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MoviesRepository(private var context: Context?) {

    private var instance : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var userRef : DatabaseReference
    private var auth = FirebaseAuth.getInstance()
    private var userId = auth.currentUser!!.uid

    init{
        userRef = instance.reference.child("users").child(userId)
    }

    fun addBookmark(reference : String){
        userRef.child("Bookmark").child("reference").setValue(reference)
    }



}