package com.example.assignment_mob_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText

class SignInFragment : Fragment() {
    private lateinit var credentialsManager: CredentialsManager

    // Views
    private val emailLayout: TextInputLayout by lazy {
        requireView().findViewById(R.id.tilEmail)
    }
    private val passwordLayout: TextInputLayout by lazy {
        requireView().findViewById(R.id.tilPassword)
    }
    private val emailInput: TextInputEditText by lazy {
        requireView().findViewById(R.id.etEmail)
    }
    private val passwordInput: TextInputEditText by lazy {
        requireView().findViewById(R.id.etPassword)
    }
    private val nextButton: MaterialButton by lazy {
        requireView().findViewById(R.id.btnNext)
    }
    private val registerNowText: TextView by lazy {
        requireView().findViewById(R.id.tvRegisterNow)
    }

    companion object {
        fun newInstance() = SignInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the shared CredentialsManager instance from MainActivity
        credentialsManager = (requireActivity() as MainActivity).getCredentialsManager()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        nextButton.setOnClickListener {
            validateAndLogin()
        }

        registerNowText.setOnClickListener {
            navigateToCreateAccount()
        }
    }

    private fun validateAndLogin() {
        val email = emailInput.text?.toString() ?: ""
        val password = passwordInput.text?.toString() ?: ""

        // Reset previous errors
        emailLayout.error = null
        passwordLayout.error = null

        // Validate email
        if (!credentialsManager.isEmailValid(email)) {
            emailLayout.error = "Please enter a valid email address"
            return
        }

        // Validate password
        if (!credentialsManager.isPasswordValid(password)) {
            passwordLayout.error = "Please enter a password"
            return
        }

        // Check credentials
        if (credentialsManager.login(email, password)) {
            // Success - you can replace this with navigation to your main app content
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        } else {
            passwordLayout.error = "Invalid email or password"
        }
    }

    private fun navigateToCreateAccount() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CreateAccountFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}