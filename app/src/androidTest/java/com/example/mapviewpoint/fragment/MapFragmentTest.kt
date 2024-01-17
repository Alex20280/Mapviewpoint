package com.example.mapviewpoint.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.example.mapviewpoint.MainActivity
import com.example.mapviewpoint.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MapFragmentTest {


    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Rule
    @JvmField
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)

    @Rule
    @JvmField
    val someGrantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.ACCESS_COARSE_LOCATION)


    @Test
    fun testMapVisibility() {
        val validLogin = "aleksandrbasanets2012@gmail.com"
        val validPassword = "cdcdF*25TT"

        onView(withId(R.id.editTextEmail))
            .perform(ViewActions.typeText(validLogin), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.editTextPassword))
            .perform(ViewActions.typeText(validPassword), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.button)).perform(click())

        Thread.sleep(5000)

        onView(withId(R.id.map))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


    }

    @Test
    fun testExitClick() {
        val validLogin = "aleksandrbasanets2012@gmail.com"
        val validPassword = "cdcdF*25TT"

        onView(withId(R.id.editTextEmail))
            .perform(ViewActions.typeText(validLogin), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.editTextPassword))
            .perform(ViewActions.typeText(validPassword), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.button)).perform(click())

        Thread.sleep(5000)

        onView(withId(R.id.exit_menu_item)).perform(click())

    }

    @Test
    fun testDatePickerClick() {
        val validLogin = "aleksandrbasanets2012@gmail.com"
        val validPassword = "cdcdF*25TT"

        onView(withId(R.id.editTextEmail))
            .perform(ViewActions.typeText(validLogin), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.editTextPassword))
            .perform(ViewActions.typeText(validPassword), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.button)).perform(click())

        Thread.sleep(5000)

        onView(withId(R.id.toolbar)).perform(click())
    }

    @Test
    fun myCurrentLocationButtonClick() {
        val validLogin = "aleksandrbasanets2012@gmail.com"
        val validPassword = "cdcdF*25TT"

        onView(withId(R.id.editTextEmail))
            .perform(ViewActions.typeText(validLogin), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.editTextPassword))
            .perform(ViewActions.typeText(validPassword), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.button)).perform(click())

        Thread.sleep(5000)

        onView(withId(R.id.fab)).perform(click())

        Thread.sleep(5000)
    }

}