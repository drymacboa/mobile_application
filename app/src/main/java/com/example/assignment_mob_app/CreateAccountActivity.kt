package com.example.assignment_mob_app
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText

class CreateAccountActivity : AppCompatActivity() {
    private val credentialsManager = CredentialsManager()

    private val fullNameLayout: TextInputLayout by lazy { findViewById(R.id.tilFullName) }
    private val emailLayout: TextInputLayout by lazy { findViewById(R.id.tilEmail) }
    private val phoneLayout: TextInputLayout by lazy { findViewById(R.id.tilPhoneNumber) }
    private val passwordLayout: TextInputLayout by lazy { findViewById(R.id.tilPassword) }

    private val fullNameInput: TextInputEditText by lazy { findViewById(R.id.etFullName) }
    private val emailInput: TextInputEditText by lazy { findViewById(R.id.etEmail) }
    private val phoneInput: TextInputEditText by lazy { findViewById(R.id.etPhoneNumber) }
    private val passwordInput: TextInputEditText by lazy { findViewById(R.id.etPassword) }

    private val termsCheckBox: MaterialCheckBox by lazy { findViewById(R.id.cbTermsAndConditions) }
    private val registerButton: MaterialButton by lazy { findViewById(R.id.btnNext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_acc)

        findViewById<TextView>(R.id.tvAlreadyMember).setOnClickListener {
            navigateToSignIn()
        }

        registerButton.setOnClickListener {
            handleRegistration()
        }
    }

    private fun handleRegistration() {
        val fullName = fullNameInput.text?.toString() ?: ""
        val email = emailInput.text?.toString() ?: ""
        val phone = phoneInput.text?.toString() ?: ""
        val password = passwordInput.text?.toString() ?: ""

        // Reset previous errors
        fullNameLayout.error = null
        emailLayout.error = null
        phoneLayout.error = null
        passwordLayout.error = null

        // Check terms and conditions
        if (!termsCheckBox.isChecked) {
            termsCheckBox.error = "Please accept terms and conditions"
            return
        }

        val result = credentialsManager.register(email, password, fullName, phone)

        result.fold(
            onSuccess = {
                // Registration successful, navigate back to sign in
                navigateToSignIn()
            },
            onFailure = { error ->
                // Show appropriate error message
                when (error.message) {
                    "Invalid email format" -> emailLayout.error = "Please enter a valid email address"
                    "Invalid password" -> passwordLayout.error = "Please enter a valid password"
                    "Please enter your full name" -> fullNameLayout.error = "Please enter your full name"
                    "Invalid phone number" -> phoneLayout.error = "Please enter a valid phone number"
                    "Email already registered" -> emailLayout.error = "Email already registered"
                    else -> emailLayout.error = "Registration failed"
                }
            }
        )
    }

    private fun navigateToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}