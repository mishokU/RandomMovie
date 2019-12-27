package com.example.randommovie.data.repository.firebase

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.randommovie.data.vo.models.BookmarkModel
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

    fun addBookmark(movie : BookmarkModel) {
        val key: String? = instance.reference.child("users").child(userId).push().key
        userRef.child("Bookmark").child(key.toString()).child("id").setValue(movie.id)
        userRef.child("Bookmark").child(key.toString()).child("title").setValue(movie.title)
        userRef.child("Bookmark").child(key.toString()).child("description").setValue(movie.description)
        userRef.child("Bookmark").child(key.toString()).child("rating").setValue(movie.rating)
        userRef.child("Bookmark").child(key.toString()).child("image_url").setValue(movie.image_url)
        userRef.child("Bookmark").child(key.toString()).child("release_data").setValue(movie.release_data)
        Toast.makeText(context,"Bookmark added", Toast.LENGTH_SHORT).show()
    }

    fun addFavourite(movie : BookmarkModel){
        val key: String? = instance.reference.child("users").child(userId).push().key
        userRef.child("Favourites").child(key.toString()).child("id").setValue(movie.id)
        userRef.child("Favourites").child(key.toString()).child("title").setValue(movie.title)
        userRef.child("Favourites").child(key.toString()).child("description").setValue(movie.description)
        userRef.child("Favourites").child(key.toString()).child("rating").setValue(movie.rating)
        userRef.child("Favourites").child(key.toString()).child("image_url").setValue(movie.image_url)
        userRef.child("Favourites").child(key.toString()).child("release_data").setValue(movie.release_data)
        Toast.makeText(context,"Favourite added", Toast.LENGTH_SHORT).show()
    }

    fun addViewed(movie : BookmarkModel){
        val key: String? = instance.reference.child("users").child(userId).push().key
        userRef.child("Views").child(key.toString()).child("id").setValue(movie.id)
        userRef.child("Views").child(key.toString()).child("title").setValue(movie.title)
        userRef.child("Views").child(key.toString()).child("description").setValue(movie.description)
        userRef.child("Views").child(key.toString()).child("rating").setValue(movie.rating)
        userRef.child("Views").child(key.toString()).child("image_url").setValue(movie.image_url)
        userRef.child("Views").child(key.toString()).child("release_data").setValue(movie.release_data)
        Toast.makeText(context,"Viewed added added", Toast.LENGTH_SHORT).show()

    }






}