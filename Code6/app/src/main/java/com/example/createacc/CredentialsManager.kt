package com.example.createacc

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CredentialsManager(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)
    private val map: MutableMap<String, String> = mutableMapOf()

    // StateFlow to manage login state
    private val _isLoggedIn = MutableStateFlow(false) // Default to logged out
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    init {
        // Load all saved credentials into the map
        preferences.all.forEach { (key, value) ->
            if (value is String) {
                map[key] = value
            }
        }
    }

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty()
    }

    fun registerAccount(email: String, password: String): String {
        val normalizedEmail = email.trim().lowercase()

        return if (map.containsKey(normalizedEmail)) {
            "Email already exists."
        } else {
            map[normalizedEmail] = password
            preferences.edit()
                .putString(normalizedEmail, password)
                .apply()
            "Registration successful."
        }
    }

    fun login(email: String, password: String): String {
        val normalizedEmail = email.trim().lowercase()

        return if (map[normalizedEmail] == password) {
            _isLoggedIn.value = true
            "Login successful."
        } else {
            "Invalid email or password."
        }
    }

    fun logout() {
        _isLoggedIn.value = false
    }
}
