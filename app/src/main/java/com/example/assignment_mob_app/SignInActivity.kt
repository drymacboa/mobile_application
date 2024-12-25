package com.example.assignment_mob_app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText

class SignInActivity : AppCompatActivity() {
    // Views as properties using custom getters
    private val emailLayout: TextInputLayout by lazy {
        findViewById(R.id.tilEmail)
    }
    private val passwordLayout: TextInputLayout by lazy {
        findViewById(R.id.tilPassword)
    }
    private val emailInput: TextInputEditText by lazy {
        findViewById(R.id.etEmail)
    }
    private val passwordInput: TextInputEditText by lazy {
        findViewById(R.id.etPassword)
    }
    private val nextButton: MaterialButton by lazy {
        findViewById(R.id.btnNext)
    }
    private val registerNowText: TextView by lazy {
        findViewById(R.id.tvRegisterNow)
    }

    // CredentialsManager instance
    private val credentialsManager = CredentialsManager()

    // Hardcoded credentials
    companion object {
        const val VALID_EMAIL = "test@te.st"
        const val VALID_PASSWORD = "1234"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)

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
        if (email == VALID_EMAIL && password == VALID_PASSWORD) {
            navigateToMain()
        } else {
            passwordLayout.error = "Invalid email or password"
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun navigateToCreateAccount() {
        val intent = Intent(this, CreateAccountActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
