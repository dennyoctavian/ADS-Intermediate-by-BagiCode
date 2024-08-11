package com.dennyoctavian.movieadsintermediate.models

import android.database.Cursor
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmModel(
    var id: Int? = 0,
    var title: String? = "",
    var description: String? = "",
    var genre: String? = "",
    var poster: Int? = 0,
    var trailer: Int? = 0,
    var rating: Float? = 0.0f,
) : Parcelable {
    companion object {
        fun fromCursor(cursor: Cursor): ArrayList<FilmModel> {
            val filmList = ArrayList<FilmModel>()

            if (cursor.moveToFirst()) {
                do {
                    val film = FilmModel(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                        description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        genre = cursor.getString(cursor.getColumnIndexOrThrow("genre")),
                        poster = cursor.getInt(cursor.getColumnIndexOrThrow("poster")),
                        trailer = cursor.getInt(cursor.getColumnIndexOrThrow("trailer")),
                        rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"))
                    )
                    filmList.add(film)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return filmList
        }
    }
}

