package com.example.createacc

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class SignupActivity : AppCompatActivity() {

    private var buttonLogin: MaterialTextView? = null
    private lateinit var credentialsManager: CredentialsManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        credentialsManager = CredentialsManager(this)

        val fullNameInput = findViewById<TextInputEditText>(R.id.fullNameInput)
        val emailInput = findViewById<TextInputEditText>(R.id.emailInput)
        val phoneInput = findViewById<TextInputEditText>(R.id.phoneInput)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordInput)
        val nextButton = findViewById<MaterialButton>(R.id.nextButton)
        buttonLogin = findViewById(R.id.loginText)

        buttonLogin?.setOnClickListener {

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        nextButton.setOnClickListener {
            val fullName = fullNameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (fullName.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
                showToast("All fields must be filled out.")
                return@setOnClickListener
            }

            val result = credentialsManager.registerAccount(email, password)
            showToast(result)

            if (result == "Registration successful.") {
                startActivity(Intent(this, Login::class.java))
                finish()
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
