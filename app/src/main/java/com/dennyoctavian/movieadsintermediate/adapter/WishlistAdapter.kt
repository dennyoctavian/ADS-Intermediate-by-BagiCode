package com.dennyoctavian.movieadsintermediate.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dennyoctavian.movieadsintermediate.R
import com.dennyoctavian.movieadsintermediate.models.FilmModel
import com.dennyoctavian.movieadsintermediate.modules.wishlist.WishlistActivity

class WishlistAdapter(
    private val movies: List<FilmModel>,
    private val listener: WishlistActivity
) : RecyclerView.Adapter<WishlistAdapter.MyViewHolder>() {
    private lateinit var ContextAdapter: Context

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.title)
        val poster: ImageView = itemView.findViewById(R.id.imageMovie)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
        val rating: RatingBar = itemView.findViewById(R.id.ratingBar)

        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_vertical, parent, false)
        ContextAdapter = parent.context
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.v("TOTAL_ITEM", movies.size.toString())
        return movies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = movies[position]
        holder.title.text = currentItem.title
        Glide.with(ContextAdapter).load(currentItem.poster).into(holder.poster)
        holder.description.text = currentItem.description
        holder.rating.numStars = 5
        holder.rating.rating = currentItem.rating ?: 0f
    }

}
