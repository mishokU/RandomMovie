package com.example.randommovie.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.randommovie.R
import com.example.randommovie.data.vo.models.BookmarkModel
import com.example.randommovie.ui.adapters.ProfileObjectsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_bookmarks.*
import java.util.ArrayList

class ActivityProfileFavourites : AppCompatActivity() {

    private var instance : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var userId : String

    private lateinit var adapter : ProfileObjectsAdapter
    private lateinit var recyclerView: RecyclerView

    private var favourites: ArrayList<BookmarkModel> = ArrayList()
    private lateinit var toolbat : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)

        userId = auth.currentUser!!.uid

        initAdapter()
        initToolbar()
        initFirebaseMoviesIds()
    }

    private fun initAdapter(){
        recyclerView = bookmark_recycler_view
        adapter = ProfileObjectsAdapter(favourites, this)

        // Creates a vertical Layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initToolbar(){
        toolbat = toolbar
        setSupportActionBar(toolbat)
        if(supportActionBar != null){
            supportActionBar?.title = "Favourites"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initFirebaseMoviesIds(){
        val database : DatabaseReference = instance.reference.child("users").child(userId).child("Favourites")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnaphot: DataSnapshot) {
                for(data in dataSnaphot.children){
                    val movie : BookmarkModel? = data.getValue(BookmarkModel::class.java)
                    if(movie != null){
                        favourites.add(movie)
                        adapter.notifyDataSetChanged()
                    }
                }
                if(favourites.count() == 0){
                    objects_empty_list.visibility = View.VISIBLE
                    objects_progress_bar.visibility = View.GONE
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

}