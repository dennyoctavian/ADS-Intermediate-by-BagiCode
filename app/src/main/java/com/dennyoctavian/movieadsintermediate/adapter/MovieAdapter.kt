package com.dennyoctavian.movieadsintermediate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dennyoctavian.movieadsintermediate.R
import com.dennyoctavian.movieadsintermediate.models.FilmModel
import com.dennyoctavian.movieadsintermediate.modules.home.MainActivity

class MovieAdapter(
    private val movies: List<FilmModel>,
    private val listener: MainActivity
) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {
    lateinit var ContextAdapter: Context

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.title)
        val poster: ImageView = itemView.findViewById(R.id.imageMovie)


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
                .inflate(R.layout.item_movie_horizontal, parent, false)
        ContextAdapter = parent.context
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = movies[position]
        holder.title.text = currentItem.title
        Glide.with(ContextAdapter).load(currentItem.poster).into(holder.poster)
    }

}
