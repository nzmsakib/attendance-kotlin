package com.nasakib.attendancems

import android.content.Context
import android.content.SharedPreferences

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager (context: Context) {
    val isLoggedIn: Boolean
        get() {
            return fetchAuthToken() != null
        }
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val TOKEN = "token"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(TOKEN, null)
    }

    /**
     * Function to clear auth token
     */
    fun clearAuthToken() {
        val editor = prefs.edit()
        editor.remove(TOKEN)
        editor.apply()
    }
}