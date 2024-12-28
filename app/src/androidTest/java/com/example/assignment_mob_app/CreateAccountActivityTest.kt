package com.example.assignment_mob_app

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.textfield.TextInputLayout

@RunWith(AndroidJUnit4::class)
class CreateAccountActivityTest {

    @Test
    fun invalidEmailShowsError() {
        ActivityScenario.launch(CreateAccountActivity::class.java).use {
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
        ActivityScenario.launch(CreateAccountActivity::class.java).use {
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
        ActivityScenario.launch(CreateAccountActivity::class.java).use {
            onView(withId(R.id.etFullName)).perform(typeText("John Doe"), closeSoftKeyboard())
            onView(withId(R.id.etEmail)).perform(typeText("john@example.com"), closeSoftKeyboard())
            onView(withId(R.id.etPhoneNumber)).perform(typeText("1234567890"), closeSoftKeyboard())
            onView(withId(R.id.etPassword)).perform(typeText("password123"), closeSoftKeyboard())
            onView(withId(R.id.cbTermsAndConditions)).perform(click())
            onView(withId(R.id.btnNext)).perform(click())

            // Activity should finish
            it.onActivity { activity ->
                assert(activity.isFinishing)
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