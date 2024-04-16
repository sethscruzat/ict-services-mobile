package com.example.ict_services_mobile.screens.loading

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences =
        application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

    fun getUserID(): Int {
        return sharedPreferences.getInt("userID", -1)
    }

    fun setUserID(userID: Int) {
        sharedPreferences.edit().putInt("userID", userID).apply()
    }

    fun getUserRole(): String? {
        return sharedPreferences.getString("role", null)
    }

    fun setUserRole(role: String) {
        sharedPreferences.edit().putString("role", role).apply()
    }

    fun logout() {
        sharedPreferences.edit().remove("userID").apply()
        sharedPreferences.edit().remove("role").apply()
    }
}