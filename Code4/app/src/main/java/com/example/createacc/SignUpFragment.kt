package com.example.createacc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class SignUpFragment : Fragment() {

    private var buttonLogin: MaterialTextView? = null
    private lateinit var credentialsManager: CredentialsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        credentialsManager = CredentialsManager(requireContext())

        val fullNameInput = view.findViewById<TextInputEditText>(R.id.fullNameInput)
        val emailInput = view.findViewById<TextInputEditText>(R.id.emailInput)
        val phoneInput = view.findViewById<TextInputEditText>(R.id.phoneInput)
        val passwordInput = view.findViewById<TextInputEditText>(R.id.passwordInput)
        val nextButton = view.findViewById<MaterialButton>(R.id.nextButton)
        buttonLogin = view.findViewById(R.id.loginText)

        buttonLogin?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LoginFragment())
                .addToBackStack(null)
                .commit()
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
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, LoginFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
