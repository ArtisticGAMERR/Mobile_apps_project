package com.example.createacc

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class Login : AppCompatActivity() {

    private lateinit var email: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var inputEditText: TextInputEditText
    private lateinit var textInputEditText: TextInputEditText
    private lateinit var next: MaterialButton
    private lateinit var rejistered: MaterialTextView
    private lateinit var checkbox: CheckBox

    private lateinit var credentialsManager: CredentialsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin)

        email = findViewById(R.id.tilEmail)
        password = findViewById(R.id.tilPassword)
        inputEditText = findViewById(R.id.emailInput)
        textInputEditText = findViewById(R.id.passwordInput)
        rejistered = findViewById(R.id.loginText)
        next = findViewById(R.id.nextButton)
        checkbox = findViewById(R.id.termsCheckbox)
        credentialsManager = CredentialsManager(this)
        next.setOnClickListener {
            val email = inputEditText.text.toString().trim()
            val password = textInputEditText.text.toString().trim()

            if (credentials(email, password)) {
                if (email.equals("test@te.st", ignoreCase = true) && password == "1234") {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    showError("Invalid email or password. Please try again.")
                }
            }
        }
    }

    private fun credentials(email: String, password: String): Boolean {
        var isValid = true

        if (!credentialsManager.isValidEmail(email)) {
            this.email.error = "Invalid email format"
            isValid = false
        } else {
            this.email.error = null
        }
        if (password.isEmpty()) {
            this.password.error = "Password cannot be empty"
            isValid = false
        } else {
            this.password.error = null
        }

        return isValid
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
