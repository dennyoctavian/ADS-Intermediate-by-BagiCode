package com.dennyoctavian.movieadsintermediate.utils

import android.content.Context
import com.dennyoctavian.movieadsintermediate.utils.Constanta.NAMA_USER
import com.dennyoctavian.movieadsintermediate.utils.Constanta.PREFS_NAME
import com.dennyoctavian.movieadsintermediate.utils.Constanta.STATUS_USER

class UserPreference(context: Context) {
    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setStatusUser(value: Boolean) {
        val editor = preference.edit()
        editor.putBoolean(STATUS_USER, value)
        editor.apply()
    }

    fun getStatusUser(): Boolean {
        return preference.getBoolean(STATUS_USER, false)
    }

    fun setNameUser(value: String) {
        val editor = preference.edit()
        editor.putString(NAMA_USER, value)
        editor.apply()
    }

    fun getNameUser(): String? {
        return preference.getString(NAMA_USER, null)
    }

    fun clearUser(): Unit {
        val editor = preference.edit()
        editor.clear()
        editor.apply()
    }

}