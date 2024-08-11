package com.dennyoctavian.movieadsintermediate.modules.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dennyoctavian.movieadsintermediate.R
import com.dennyoctavian.movieadsintermediate.adapter.MovieAdapter
import com.dennyoctavian.movieadsintermediate.models.FilmModel
import com.dennyoctavian.movieadsintermediate.modules.all_movies.AllMovieActivity
import com.dennyoctavian.movieadsintermediate.modules.detail.DetailActivity
import com.dennyoctavian.movieadsintermediate.modules.login.LoginActivity
import com.dennyoctavian.movieadsintermediate.modules.wishlist.WishlistActivity
import com.dennyoctavian.movieadsintermediate.utils.UserPreference
import com.facebook.shimmer.ShimmerFrameLayout

class MainActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener {
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movies: ArrayList<FilmModel>
    private lateinit var viewAll: TextView
    private lateinit var tvLogin: TextView
    private lateinit var tvDescription: TextView
    private lateinit var ivLogout: ImageView
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var userPreference: UserPreference
    private lateinit var mainActivityResultLauncher: ActivityResultLauncher<Intent>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mainActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bundle: Intent? = result.data
                // Handle the result here, e.g., extract data from the intent
                initView()
            }
        }

        movies = arrayListOf(
            FilmModel(
                1,
                "A Rainy Day in New York",
                "Two young people arrive in New York to spend a weekend, but once they arrive they're met with bad weather and a series of adventures.",
                "Comedy, Romance",
                R.drawable.ic_poster_a_rainy_day_in_new_york,
                R.raw.video_a_rainy_day,
                4.0F
            ),
            FilmModel(
                2,
                "Sonic the Hedgehog",
                "Based on the global blockbuster videogame franchise from Sega, Sonic the Hedgehog tells the story of the world’s speediest hedgehog as he embraces his new home on Earth. In this live-action adventure comedy, Sonic and his new best friend team up to defend the planet from the evil genius Dr. Robotnik and his plans for world domination.",
                "Comedy, Action, Family",
                R.drawable.ic_poster_sonic,
                R.raw.video_sonic,
                3.0F
            ),
            FilmModel(
                3,
                "Ad Astra",
                "The near future, a time when both hope and hardships drive humanity to look to the stars and beyond. While a mysterious phenomenon menaces to destroy life on planet Earth, astronaut Roy McBride undertakes a mission across the immensity of space and its many perils to uncover the truth about a lost expedition that decades before boldly faced emptiness and silence in search of the unknown.",
                "Drama, Advanture, Mystery",
                R.drawable.ic_ad_astra,
                R.raw.video_sample,
                2.0F
            ),
            FilmModel(
                4,
                "Avengers: Endgame",
                "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
                "Action, Time Travel, Avengers",
                R.drawable.ic_avengers,
                R.raw.video_sample,
                5.0F
            )
        )

        initView()

        shimmer.startShimmer()
        Handler(Looper.getMainLooper()).postDelayed({
            shimmer.stopShimmer()
            movieRecyclerView.visibility = View.VISIBLE
            shimmer.visibility = View.GONE
        }, 5000) // 3000 is the delayed time in milliseconds.


        viewAll.setOnClickListener {
            val intent = Intent(this, AllMovieActivity::class.java).putExtra("data", movies)
            startActivity(intent)
        }

        tvLogin.setOnClickListener {
            if (userPreference.getStatusUser()) {
                val intent = Intent(this, WishlistActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                mainActivityResultLauncher.launch(intent)
            }
        }

        ivLogout.setOnClickListener {
            userPreference.clearUser()
            initView()
        }

    }

    private fun initView() {
        movieRecyclerView = findViewById(R.id.movieRecycleView)
        viewAll = findViewById(R.id.tvViewAll)
        tvLogin = findViewById(R.id.tvLogin)
        tvDescription = findViewById(R.id.tvDescription)
        ivLogout = findViewById(R.id.iv_logout)
        shimmer = findViewById(R.id.shimmerListMovie)
        userPreference = UserPreference(this)


        movieRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movieAdapter = MovieAdapter(movies, this)
        movieRecyclerView.adapter = movieAdapter

        if (userPreference.getStatusUser()) {
            tvLogin.text = userPreference.getNameUser()
            tvDescription.text = "Thanks for join, do you want exit?"
            ivLogout.visibility = View.VISIBLE
        } else {
            tvLogin.text = "Login"
            tvDescription.text = "Save your favorite movie"
            ivLogout.visibility = View.INVISIBLE
        }

    }

    override fun onItemClick(position: Int) {
        val clickedItem = movies[position]
        val intent = Intent(this, DetailActivity::class.java).putExtra("data", clickedItem)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

}