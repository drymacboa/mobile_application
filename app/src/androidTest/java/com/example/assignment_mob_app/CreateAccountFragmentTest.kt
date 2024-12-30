package com.example.assignment_mob_app

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.textfield.TextInputLayout

@RunWith(AndroidJUnit4::class)
class CreateAccountFragmentTest {

    @Test
    fun invalidEmailShowsError() {
        launchFragmentInContainer<CreateAccountFragment>().use { scenario ->
            // Fill in valid data except email
            onView(withId(R.id.etFullName)).perform(typeText("John Doe"), closeSoftKeyboard())
            onView(withId(R.id.etEmail)).perform(typeText("invalid-email"), closeSoftKeyboard())
            onView(withId(R.id.etPhoneNumber)).perform(typeText("1234567890"), closeSoftKeyboard())
            onView(withId(R.id.etPassword)).perform(typeText("password123"), closeSoftKeyboard())
            onView(withId(R.id.cbTermsAndConditions)).perform(click())
            onView(withId(R.id.btnNext)).perform(click())

            onView(withId(R.id.tilEmail)).check(matches(hasError("Please enter a valid email address")))
        }
    }

    @Test
    fun missingFullNameShowsError() {
        launchFragmentInContainer<CreateAccountFragment>().use { scenario ->
            onView(withId(R.id.etEmail)).perform(typeText("test@example.com"), closeSoftKeyboard())
            onView(withId(R.id.etPhoneNumber)).perform(typeText("1234567890"), closeSoftKeyboard())
            onView(withId(R.id.etPassword)).perform(typeText("password123"), closeSoftKeyboard())
            onView(withId(R.id.cbTermsAndConditions)).perform(click())
            onView(withId(R.id.btnNext)).perform(click())

            onView(withId(R.id.tilFullName)).check(matches(hasError("Please enter your full name")))
        }
    }

    @Test
    fun successfulRegistrationNavigatesToSignIn() {
        launchFragmentInContainer<CreateAccountFragment>().use { scenario ->
            onView(withId(R.id.etFullName)).perform(typeText("John Doe"), closeSoftKeyboard())
            onView(withId(R.id.etEmail)).perform(typeText("john@example.com"), closeSoftKeyboard())
            onView(withId(R.id.etPhoneNumber)).perform(typeText("1234567890"), closeSoftKeyboard())
            onView(withId(R.id.etPassword)).perform(typeText("password123"), closeSoftKeyboard())
            onView(withId(R.id.cbTermsAndConditions)).perform(click())
            onView(withId(R.id.btnNext)).perform(click())

            // Check if fragment is destroyed after successful registration
            scenario.onFragment { fragment ->
                assert(fragment.isDetached)
            }
        }
    }

    private fun hasError(expectedError: String) = object : androidx.test.espresso.matcher.BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
        override fun describeTo(description: org.hamcrest.Description?) {
            description?.appendText("has error: $expectedError")
        }

        override fun matchesSafely(item: TextInputLayout): Boolean {
            return item.error?.toString() == expectedError
        }
    }
}