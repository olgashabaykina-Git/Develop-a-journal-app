package com.example.mypersonaldiary7.ul.activities

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mypersonaldiary7.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    /**
     * Test: Switching to AddEntryActivity when clicking on the "Add" button
     */
    @Test
    fun testAddButtonOpensAddEntryActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.use {
            onView(withId(R.id.addButton)).perform(click())
            Intents.intended(hasComponent("com.example.mypersonaldiary7.ul.activities.AddEntryActivity"))
        }
    }

    /**
     * Test: Switching to Calendar Activity when clicking on the "Calendar" button
     */
    @Test
    fun testCalendarButtonOpensCalendarActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.use {
            onView(withId(R.id.calendarButton)).perform(click())
            Intents.intended(hasComponent("com.example.mypersonaldiary7.ul.activities.CalendarActivity"))
        }
    }

    /**
     * Test: Adding an entry and opening an EditEntryActivity
     */
    @Test
    fun testAddEntryAndOpenEditEntryActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.use {
            // Click on the "Add" button
            onView(withId(R.id.addButton)).perform(click())

            //  check that AddEntryActivity has opened
            Intents.intended(hasComponent("com.example.mypersonaldiary7.ul.activities.AddEntryActivity"))

            // Entering the data of the new record
            val newEntryTitle = "Test Entry"
            onView(withId(R.id.entryEditText))
                .perform(typeText(newEntryTitle), closeSoftKeyboard())
            onView(withId(R.id.saveButton)).perform(click())

            // check that the  back in MainActivity
            onView(withId(R.id.addButton)).check(matches(isDisplayed()))

            // check that the new entry is displayed in the list
            onView(withText(newEntryTitle)).check(matches(isDisplayed()))

            // Click on the new entry
            onView(withText(newEntryTitle)).perform(click())

            //  check that the EditEntryActivity has opened
            Intents.intended(hasComponent("com.example.mypersonaldiary7.ul.activities.EditEntryActivity"))

            // check that the data is displayed correctly
            onView(withId(R.id.entryEditText)).check(matches(withText(newEntryTitle)))
        }
    }



    /**
     * Test: Checking the visibility of all buttons on the screen
     */
    @Test
    fun testButtonsAreVisibleInMainActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.use {
            onView(withId(R.id.homeButton)).check(matches(isDisplayed()))
            onView(withId(R.id.addButton)).check(matches(isDisplayed()))
            onView(withId(R.id.calendarButton)).check(matches(isDisplayed()))
        }
    }
}
