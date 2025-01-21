package com.example.createacc

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns

class CredentialsManager(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)
    private val map: MutableMap<String, String> = mutableMapOf()

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty()
    }
    init {
        preferences.all.forEach { (key, value) ->
            if (value is String) {
                map[key] = value
            }
        }
    }

    fun registerAccount(email: String, password: String): String {
        val normalizedEmail = email.trim().lowercase()

        if(map.containsKey(normalizedEmail)) {
            return "Email already exists."
        } else {
            map[normalizedEmail] = password
            val editor = preferences.edit()
            editor.putString(normalizedEmail, password)
            editor.apply()
            return "Registration successful."
        }
    }

}
