package com.dennyoctavian.movieadsintermediate.modules.all_movies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dennyoctavian.movieadsintermediate.R
import com.dennyoctavian.movieadsintermediate.adapter.AllMovieAdapter
import com.dennyoctavian.movieadsintermediate.databinding.ActivityAllMovieBinding
import com.dennyoctavian.movieadsintermediate.models.FilmModel
import com.dennyoctavian.movieadsintermediate.modules.detail.DetailActivity

class AllMovieActivity : AppCompatActivity(), AllMovieAdapter.OnItemClickListener {
    private lateinit var binding: ActivityAllMovieBinding
    private lateinit var recyclerViewAllMovie: RecyclerView
    private lateinit var movies: ArrayList<FilmModel>
    private lateinit var allMovieAdapter: AllMovieAdapter

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        recyclerViewAllMovie = findViewById(R.id.rvAllMovie)
        movies = intent.getParcelableArrayListExtra("data", FilmModel::class.java) ?: arrayListOf()

        recyclerViewAllMovie.layoutManager = LinearLayoutManager(this)
        allMovieAdapter = AllMovieAdapter(movies, this)
        recyclerViewAllMovie.adapter = allMovieAdapter
    }

    override fun onItemClick(position: Int) {
        val clickedItem = movies[position]
        val intent = Intent(this, DetailActivity::class.java).putExtra("data", clickedItem)
        startActivity(intent)
    }
}