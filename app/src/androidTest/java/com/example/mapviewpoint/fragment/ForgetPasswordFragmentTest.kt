package com.example.mapviewpoint.fragment

import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.runner.AndroidJUnit4
import com.example.mapviewpoint.MainActivity
import com.example.mapviewpoint.R
import org.hamcrest.CoreMatchers
import androidx.test.espresso.Espresso.onView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForgetPasswordFragmentTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSubmitButtonDisabledWithWrongLoginInput() {
        val validLogin = "user"

        onView(ViewMatchers.withId(R.id.textViewForgotPassword)).perform(ViewActions.click())

        // Input valid login
        onView(ViewMatchers.withId(R.id.resetPasswordEt))
            .perform(ViewActions.typeText(validLogin), ViewActions.closeSoftKeyboard())

        // Check if the login button is disabled
        onView(ViewMatchers.withId(R.id.submitBtn))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isEnabled())))
    }


    @Test
    fun testSubmitButtonEnabledWithCorrectLoginInput() {
        val validLogin = "user@example.com"

        onView(ViewMatchers.withId(R.id.textViewForgotPassword)).perform(ViewActions.click())

        // Input valid login
        onView(ViewMatchers.withId(R.id.resetPasswordEt))
            .perform(ViewActions.typeText(validLogin), ViewActions.closeSoftKeyboard())

        // Check if the login button is disabled
        onView(ViewMatchers.withId(R.id.submitBtn))
            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
    }
}