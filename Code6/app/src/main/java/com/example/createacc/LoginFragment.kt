package com.example.createacc

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class LoginFragment : Fragment() {

    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var nextButton: MaterialButton
    private lateinit var registerText: MaterialTextView
    private lateinit var termsCheckbox: CheckBox

    private lateinit var credentialsManager: CredentialsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailLayout = view.findViewById(R.id.tilEmail)
        passwordLayout = view.findViewById(R.id.tilPassword)
        emailInput = view.findViewById(R.id.emailInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        registerText = view.findViewById(R.id.loginText)
        nextButton = view.findViewById(R.id.nextButton)
        termsCheckbox = view.findViewById(R.id.termsCheckbox)
        credentialsManager = CredentialsManager(requireContext())

        nextButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (validateCredentials(email, password)) {
                if (email.equals("test@te.st", ignoreCase = true) && password == "1234") {
                    val intent = Intent(requireContext(), RecipeActivity::class.java)
                    startActivity(intent)
                } else {
                    showError("Invalid email or password. Please try again.")
                }
            }
        }
    }

    private fun validateCredentials(email: String, password: String): Boolean {
        var isValid = true

        if (!credentialsManager.isValidEmail(email)) {
            emailLayout.error = "Invalid email format"
            isValid = false
        } else {
            emailLayout.error = null
        }
        if (password.isEmpty()) {
            passwordLayout.error = "Password cannot be empty"
            isValid = false
        } else {
            passwordLayout.error = null
        }

        return isValid
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
