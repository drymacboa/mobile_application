package com.example.assignment_mob_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val credentialsManager = CredentialsManager()
    private var isFragmentA = true  // Add this line to track current fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initial fragment display (instead of showSignInFragment())
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, FragmentA.newInstance())
                .commit()
        }

        // Add button click listener for switching fragments
        findViewById<Button>(R.id.switchButton).setOnClickListener {
            if (isFragmentA) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FragmentB.newInstance())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FragmentA.newInstance())
                    .commit()
            }
            isFragmentA = !isFragmentA
        }
    }

    private fun showSignInFragment() {
        replaceFragment(SignInFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun getCredentialsManager(): CredentialsManager = credentialsManager
}