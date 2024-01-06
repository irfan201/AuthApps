package com.example.authapps

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val PREF_NAME = "MyAppPrefs"

        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_TOKEN = "userToken"
    }

    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setUserToken(token: String) {
        editor.putString(KEY_USER_TOKEN, token)
        editor.apply()
    }


}
