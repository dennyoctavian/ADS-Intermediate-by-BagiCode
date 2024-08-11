package com.dennyoctavian.movieadsintermediate.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.DESCRIPTION
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.GENRE
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.POSTER
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.RATING
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.TABLE_NAME
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.TITLE
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.TRAILER
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion._ID
import com.dennyoctavian.movieadsintermediate.database.DatabaseHelper

class MovieHelper(context: Context) {

    companion object {
        // Constants remain the same
        private const val DATABASE_NAME = TABLE_NAME
        private lateinit var databaseHelper: DatabaseHelper
        private var INSTANCE: MovieHelper? = null
        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context): MovieHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MovieHelper(context)
            }
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen) {
            database.close()
        }
    }


    // Insert a new movie
    fun insertMovie(
        id: Int?,
        title: String,
        description: String,
        genre: String,
        poster: Int,
        trailer: Int,
        rating: String
    ): Long {
        val values = ContentValues().apply {
            put(_ID, id)
            put(TITLE, title)
            put(DESCRIPTION, description)
            put(GENRE, genre)
            put(POSTER, poster)
            put(TRAILER, trailer)
            put(RATING, rating)
        }
        return database.insert(TABLE_NAME, null, values)
    }

    // Update an existing movie
    fun updateMovie(
        id: Long,
        title: String,
        description: String,
        genre: String,
        poster: String,
        trailer: String,
        rating: String
    ): Int {
        val values = ContentValues().apply {
            put(TITLE, title)
            put(DESCRIPTION, description)
            put(GENRE, genre)
            put(POSTER, poster)
            put(TRAILER, trailer)
            put(RATING, rating)
        }
        return database.update(TABLE_NAME, values, "$_ID = ?", arrayOf(id.toString()))
    }

    // Delete a movie
    fun deleteMovie(id: Long): Int {
        return database.delete(TABLE_NAME, "$_ID = ?", arrayOf(id.toString()))
    }

    // Query all movies
    fun getAllMovies(): Cursor {
        return database.query(TABLE_NAME, null, null, null, null, null, "$_ID ASC")
    }

    // Query a single movie by ID
    fun getMovieById(id: Long): Cursor {
        return database.query(
            TABLE_NAME,
            null,
            "$_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
    }
}
