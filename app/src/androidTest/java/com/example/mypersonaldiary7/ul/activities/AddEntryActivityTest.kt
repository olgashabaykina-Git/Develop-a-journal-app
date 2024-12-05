package com.example.mypersonaldiary7.ul.activities

import android.Manifest
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.example.mypersonaldiary7.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddEntryActivityTest {

    // Automatic provision of all necessary permissions
    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Before
    fun setUp() {
        // Initializing Intents to check transitions between screens
        Intents.init()
    }

    @After
    fun tearDown() {
        // Releasing Intents resources
        Intents.release()
    }

    /**
     * Test: Adding a record and displaying it on the home screen
     */
    @Test
    fun testAddEntryAndDisplayInMainActivity() {
        val scenario = ActivityScenario.launch(AddEntryActivity::class.java)
        scenario.use {
            // Entering text for a new entry
            val entryText = "My New Diary Entry"
            onView(withId(R.id.entryEditText))
                .perform(clearText(), typeText(entryText), closeSoftKeyboard())

            // Clicking the "Save" button
            onView(withId(R.id.saveButton)).perform(click())

            // Checking the transition to the main screen
            Intents.intended(hasComponent("com.example.mypersonaldiary7.ul.activities.MainActivity"))

            // Checking the display of text on the main screen
            onView(withText(entryText)).check(matches(isDisplayed()))
        }
    }

    /**
     * Test: Switching to the main screen (MainActivity)
     */
    @Test
    fun testNavigationToHome() {
        val scenario = ActivityScenario.launch(AddEntryActivity::class.java)
        scenario.use {
            onView(withId(R.id.homeButton)).perform(click())
            Intents.intended(hasComponent("com.example.mypersonaldiary7.ul.activities.MainActivity"))
        }
    }

    /**
     * Test: Switching to the calendar screen (Calendar Activity)
     */
    @Test
    fun testNavigationToCalendar() {
        val scenario = ActivityScenario.launch(AddEntryActivity::class.java)
        scenario.use {
            onView(withId(R.id.calendarButton)).perform(click())
            Intents.intended(hasComponent("com.example.mypersonaldiary7.ul.activities.CalendarActivity"))
        }
    }

    /**
     * Test: Checking the visibility of the buttons on the screen
     */
    @Test
    fun testButtonsAreVisible() {
        val scenario = ActivityScenario.launch(AddEntryActivity::class.java)
        scenario.use {
            onView(withId(R.id.homeButton)).check(matches(isDisplayed()))
            onView(withId(R.id.calendarButton)).check(matches(isDisplayed()))
            onView(withId(R.id.clipButton)).check(matches(isDisplayed()))
            onView(withId(R.id.saveButton)).check(matches(isDisplayed()))
        }
    }
}
