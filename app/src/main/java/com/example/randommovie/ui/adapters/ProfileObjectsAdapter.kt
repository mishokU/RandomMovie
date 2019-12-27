package com.example.randommovie.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.data.vo.models.BookmarkModel
import com.example.randommovie.ui.activities.SingleMovie
import kotlinx.android.synthetic.main.item_movie.view.*

class ProfileObjectsAdapter(val items : ArrayList<BookmarkModel>, val context: Context) :
    RecyclerView.Adapter<ProfileObjectsAdapter.ProfileObjectViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileObjectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_movie, parent, false)
        return ProfileObjectViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ProfileObjectViewHolder, position: Int) {
        holder.bind(items[position],context)
    }

    class ProfileObjectViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: BookmarkModel?, context: Context) {
            itemView.item_movie_title.text = movie?.title
            itemView.item_movie_description.text =  movie?.description
            itemView.item_movie_rating.text = movie?.rating
            itemView.item_movie_publication_date.text = movie?.release_data

            Glide.with(itemView.context)
                .load(movie?.image_url)
                .into(itemView.item_movie_picture)

            itemView.setOnClickListener{
                val intent = Intent(context, SingleMovie::class.java)
                intent.putExtra("id", movie?.id)
                context.startActivity(intent)
            }
        }

    }
}