package com.dennyoctavian.movieadsintermediate.modules.detail

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dennyoctavian.movieadsintermediate.R
import com.dennyoctavian.movieadsintermediate.models.FilmModel
import com.dennyoctavian.movieadsintermediate.models.MovieHelper
import com.dennyoctavian.movieadsintermediate.modules.login.LoginActivity
import com.dennyoctavian.movieadsintermediate.utils.UserPreference

class DetailActivity : AppCompatActivity() {
    private lateinit var videoViewMovie: VideoView
    private lateinit var title: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView
    private lateinit var favoriteIcon: ImageView
    private lateinit var movieHelper: MovieHelper
    private lateinit var movie: Cursor
    private lateinit var data: FilmModel
    private lateinit var userPreference: UserPreference
    private lateinit var detailActivityResultLauncher: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initField()
        initContent()
        favoriteIcon.setOnClickListener {
            if (userPreference.getStatusUser()) {
                val movie = movieHelper.getMovieById(data.id?.toLong() ?: 0L)
                if (movie.moveToNext()) {
                    val success = movieHelper.deleteMovie(data.id?.toLong() ?: 0L)
                    if (success == 1) {
                        favoriteIcon.setImageResource(R.drawable.baseline_favorite_disabled)
                        Toast.makeText(this, "Success Menghapus film favorite", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                    Log.v("RESULT", success.toString())
                } else {
                    val success = movieHelper.insertMovie(
                        data.id,
                        data.title ?: "",
                        data.description ?: "",
                        data.genre ?: "",
                        data.poster ?: 0,
                        data.trailer ?: 0,
                        data.rating.toString()
                    )
                    Log.v("RESULT2", success.toString())
                    favoriteIcon.setImageResource(R.drawable.baseline_favorite_enabled)
                    if (success == 1L) {
                        favoriteIcon.setImageResource(R.drawable.baseline_favorite_enabled)
                        Toast.makeText(
                            this,
                            "Success Menambahkan film favorite",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                detailActivityResultLauncher.launch(intent)
            }
        }
    }

    private fun initField() {
        videoViewMovie = findViewById(R.id.videoViewMovie)
        title = findViewById(R.id.tvTitle)
        genre = findViewById(R.id.tvGenre)
        description = findViewById(R.id.tvDescription)
        favoriteIcon = findViewById(R.id.iv_favorite)
        movieHelper = MovieHelper.getInstance(this)
        movieHelper.open()
        userPreference = UserPreference(this)
        detailActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bundle: Intent? = result.data
                // Handle the result here, e.g., extract data from the intent
                val movie = movieHelper.getMovieById(data.id?.toLong() ?: 0L)
                if (movie.moveToNext()) {
                    val success = movieHelper.deleteMovie(data.id?.toLong() ?: 0L)
                    if (success == 1) {
                        favoriteIcon.setImageResource(R.drawable.baseline_favorite_disabled)
                        Toast.makeText(this, "Success Menghapus film favorite", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                    Log.v("RESULT", success.toString())
                } else {
                    val success = movieHelper.insertMovie(
                        data.id,
                        data.title ?: "",
                        data.description ?: "",
                        data.genre ?: "",
                        data.poster ?: 0,
                        data.trailer ?: 0,
                        data.rating.toString()
                    )
                    Log.v("RESULT2", success.toString())
                    favoriteIcon.setImageResource(R.drawable.baseline_favorite_enabled)
                    if (success == 1L) {
                        favoriteIcon.setImageResource(R.drawable.baseline_favorite_enabled)
                        Toast.makeText(
                            this,
                            "Success Menambahkan film favorite",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initContent() {
        data = intent.getParcelableExtra("data", FilmModel::class.java) ?: FilmModel()

        title.text = data.title ?: ""
        genre.text = data.genre ?: ""
        description.text = data.description ?: ""

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoViewMovie)
        videoViewMovie.setMediaController(mediaController)

        videoViewMovie.setVideoURI(Uri.parse("android.resource://$packageName/${data.trailer}"))
        videoViewMovie.start()

        movie = movieHelper.getMovieById(data.id?.toLong() ?: 0L)
        if (movie.moveToNext()) {
            favoriteIcon.setImageResource(R.drawable.baseline_favorite_enabled)

        } else {
            favoriteIcon.setImageResource(R.drawable.baseline_favorite_disabled)

        }
    }
}