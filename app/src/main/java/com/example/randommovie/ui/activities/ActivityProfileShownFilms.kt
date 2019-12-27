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

class ActivityProfileShownFilms : ActivityProfileBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_bookmarks)
        setTitle("Views")
        super.onCreate(savedInstanceState)
    }
}