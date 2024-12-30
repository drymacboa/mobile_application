package com.example.assignment_mob_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText

class CreateAccountFragment : Fragment() {
    private lateinit var credentialsManager: CredentialsManager

    // Views
    private val fullNameLayout: TextInputLayout by lazy {
        requireView().findViewById(R.id.tilFullName)
    }
    private val emailLayout: TextInputLayout by lazy {
        requireView().findViewById(R.id.tilEmail)
    }
    private val phoneLayout: TextInputLayout by lazy {
        requireView().findViewById(R.id.tilPhoneNumber)
    }
    private val passwordLayout: TextInputLayout by lazy {
        requireView().findViewById(R.id.tilPassword)
    }
    private val fullNameInput: TextInputEditText by lazy {
        requireView().findViewById(R.id.etFullName)
    }
    private val emailInput: TextInputEditText by lazy {
        requireView().findViewById(R.id.etEmail)
    }
    private val phoneInput: TextInputEditText by lazy {
        requireView().findViewById(R.id.etPhoneNumber)
    }
    private val passwordInput: TextInputEditText by lazy {
        requireView().findViewById(R.id.etPassword)
    }
    private val termsCheckBox: MaterialCheckBox by lazy {
        requireView().findViewById(R.id.cbTermsAndConditions)
    }
    private val registerButton: MaterialButton by lazy {
        requireView().findViewById(R.id.btnNext)
    }

    companion object {
        fun newInstance() = CreateAccountFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the shared CredentialsManager instance from MainActivity
        credentialsManager = (requireActivity() as MainActivity).getCredentialsManager()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        view?.findViewById<TextView>(R.id.tvAlreadyMember)?.setOnClickListener {
            // Go back to SignInFragment
            parentFragmentManager.popBackStack()
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

        credentialsManager.register(email, password, fullName, phone)
            .fold(
                onSuccess = {
                    // Registration successful, go back to sign in
                    parentFragmentManager.popBackStack()
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
}