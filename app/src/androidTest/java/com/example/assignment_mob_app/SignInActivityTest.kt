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
class SignInActivityTest {

    @Test
    fun invalidEmailShowsError() {
        ActivityScenario.launch(SignInActivity::class.java).use {
            // Enter invalid email
            onView(withId(R.id.etEmail)).perform(typeText("invalid-email"), closeSoftKeyboard())
            onView(withId(R.id.btnNext)).perform(click())

            // Check if error is displayed
            onView(withId(R.id.tilEmail)).check(matches(hasError("Please enter a valid email address")))
        }
    }

    @Test
    fun emptyPasswordShowsError() {
        ActivityScenario.launch(SignInActivity::class.java).use {
            // Enter valid email but no password
            onView(withId(R.id.etEmail)).perform(typeText("test@te.st"), closeSoftKeyboard())
            onView(withId(R.id.btnNext)).perform(click())

            // Check if error is displayed
            onView(withId(R.id.tilPassword)).check(matches(hasError("Please enter a password")))
        }
    }

    @Test
    fun validCredentialsNavigatesToMain() {
        ActivityScenario.launch(SignInActivity::class.java).use {
            // Enter valid credentials
            onView(withId(R.id.etEmail)).perform(typeText("test@te.st"), closeSoftKeyboard())
            onView(withId(R.id.etPassword)).perform(typeText("1234"), closeSoftKeyboard())
            onView(withId(R.id.btnNext)).perform(click())

            // Activity should finish (we can't easily test if MainActivity started)
            it.onActivity { activity ->
                assert(activity.isFinishing)
            }
        }
    }

    @Test
    fun invalidCredentialsShowsError() {
        ActivityScenario.launch(SignInActivity::class.java).use {
            // Enter invalid credentials
            onView(withId(R.id.etEmail)).perform(typeText("test@te.st"), closeSoftKeyboard())
            onView(withId(R.id.etPassword)).perform(typeText("wrong-password"), closeSoftKeyboard())
            onView(withId(R.id.btnNext)).perform(click())

            // Check if error is displayed
            onView(withId(R.id.tilPassword)).check(matches(hasError("Invalid email or password")))
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