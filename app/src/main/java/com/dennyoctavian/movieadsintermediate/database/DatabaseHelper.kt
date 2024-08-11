package com.dennyoctavian.movieadsintermediate.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.DESCRIPTION
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.GENRE
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.POSTER
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.RATING
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.TABLE_NAME
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.TITLE
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion.TRAILER
import com.dennyoctavian.movieadsintermediate.database.DatabaseContract.NotesColumns.Companion._ID

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbmovie_app"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME (" +
                "$_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$TITLE TEXT NOT NULL, " +
                "$DESCRIPTION TEXT NOT NULL, " +
                "$GENRE TEXT NOT NULL, " +
                "$POSTER TEXT NOT NULL, " +
                "$TRAILER TEXT NOT NULL, " +
                "$RATING TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}