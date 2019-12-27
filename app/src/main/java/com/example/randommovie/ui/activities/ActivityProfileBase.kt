package com.example.randommovie.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.randommovie.data.vo.models.BookmarkModel
import com.example.randommovie.ui.adapters.ProfileObjectsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_bookmarks.*
import java.util.ArrayList

abstract class ActivityProfileBase : AppCompatActivity() {

    private var instance : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var userId : String

    private lateinit var adapter : ProfileObjectsAdapter
    private lateinit var recyclerView: RecyclerView

    private var items: ArrayList<BookmarkModel> = ArrayList()
    private lateinit var toolbat : Toolbar
    private lateinit var title : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userId = auth.currentUser!!.uid

        initAdapter()
        initToolbar()
        initFirebaseMoviesIds()
    }

    @Override
    protected fun setTitle(title : String){
        this.title = title
    }


    private fun initAdapter(){
        recyclerView = bookmark_recycler_view
        adapter = ProfileObjectsAdapter(items, this)

        // Creates a vertical Layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initToolbar(){
        toolbat = toolbar
        setSupportActionBar(toolbat)
        if(supportActionBar != null){
            supportActionBar?.title = title
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initFirebaseMoviesIds(){
        val database : DatabaseReference = instance.reference.child("users").child(userId).child(title)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnaphot: DataSnapshot) {
                for(data in dataSnaphot.children){
                    val movie : BookmarkModel? = data.getValue(BookmarkModel::class.java)
                    if(movie != null){
                        items.add(movie)
                        adapter.notifyDataSetChanged()
                    }
                }
                if(items.count() == 0){
                    objects_empty_list.visibility = View.VISIBLE
                    objects_progress_bar.visibility = View.GONE
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }
}