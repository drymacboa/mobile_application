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
class SignInFragmentTest {

    @Test
    fun invalidEmailShowsError() {
        launchFragmentInContainer<SignInFragment>().use { scenario ->
            onView(withId(R.id.etEmail)).perform(typeText("invalid-email"), closeSoftKeyboard())
            onView(withId(R.id.btnNext)).perform(click())

            onView(withId(R.id.tilEmail)).check(matches(hasError("Please enter a valid email address")))
        }
    }

    @Test
    fun emptyPasswordShowsError() {
        launchFragmentInContainer<SignInFragment>().use { scenario ->
            onView(withId(R.id.etEmail)).perform(typeText("test@te.st"), closeSoftKeyboard())
            onView(withId(R.id.btnNext)).perform(click())

            onView(withId(R.id.tilPassword)).check(matches(hasError("Please enter a password")))
        }
    }

    @Test
    fun validCredentialsNavigatesToMain() {
        launchFragmentInContainer<SignInFragment>().use { scenario ->
            onView(withId(R.id.etEmail)).perform(typeText("test@te.st"), closeSoftKeyboard())
            onView(withId(R.id.etPassword)).perform(typeText("1234"), closeSoftKeyboard())
            onView(withId(R.id.btnNext)).perform(click())

            scenario.onFragment { fragment ->
                assert(fragment.isDetached)
            }
        }
    }

    @Test
    fun invalidCredentialsShowsError() {
        launchFragmentInContainer<SignInFragment>().use { scenario ->
            onView(withId(R.id.etEmail)).perform(typeText("test@te.st"), closeSoftKeyboard())
            onView(withId(R.id.etPassword)).perform(typeText("wrong-password"), closeSoftKeyboard())
            onView(withId(R.id.btnNext)).perform(click())

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