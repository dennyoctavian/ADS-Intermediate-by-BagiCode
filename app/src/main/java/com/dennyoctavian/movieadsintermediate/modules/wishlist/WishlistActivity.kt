package com.dennyoctavian.movieadsintermediate.modules.wishlist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dennyoctavian.movieadsintermediate.R
import com.dennyoctavian.movieadsintermediate.adapter.WishlistAdapter
import com.dennyoctavian.movieadsintermediate.databinding.ActivityWishlistBinding
import com.dennyoctavian.movieadsintermediate.models.FilmModel
import com.dennyoctavian.movieadsintermediate.models.MovieHelper
import com.dennyoctavian.movieadsintermediate.modules.detail.DetailActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WishlistActivity : AppCompatActivity(), WishlistAdapter.OnItemClickListener {
    private lateinit var binding: ActivityWishlistBinding
    private lateinit var recyclerViewWishlistMovie: RecyclerView
    private lateinit var movies: ArrayList<FilmModel>
    private lateinit var wishlistMovieAdapter: WishlistAdapter
    private lateinit var helper: MovieHelper

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        helper = MovieHelper(this)
        helper.open()

        movies = arrayListOf()

        GlobalScope.launch(Dispatchers.Main) {
            val defferValue = async(Dispatchers.IO) {
                val cursor = helper.getAllMovies()
                FilmModel.fromCursor(cursor)
            }
            movies = defferValue.await()
            Log.v("MOVIES", movies.size.toString())
            initView()
        }

        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)

        initView()
    }

    fun initView() {
        recyclerViewWishlistMovie = findViewById(R.id.rvWishlistMovie)
        recyclerViewWishlistMovie.layoutManager = LinearLayoutManager(this)
        wishlistMovieAdapter = WishlistAdapter(movies, this)
        recyclerViewWishlistMovie.adapter = wishlistMovieAdapter
    }

    override fun onItemClick(position: Int) {
        val clickedItem = movies[position]
        val intent = Intent(this, DetailActivity::class.java).putExtra("data", clickedItem)
        startActivity(intent)
    }
}