package com.example.mypersonaldiary7.ul.activities

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.mypersonaldiary7.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class CalendarActivityTest {

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    /**
     * Test: Checking the Home button
     */
    @Test
    fun testHomeButtonNavigatesToMainActivity() {
        val scenario = ActivityScenario.launch(CalendarActivity::class.java)
        scenario.use {
            // Click on the "Home" button
            onView(withId(R.id.homeButton)).perform(click())

            // Checking that the MainActivity is open
            Intents.intended(hasComponent(MainActivity::class.java.name))
        }
    }

    /**
     * Test: Checking the "Add" button
     */
    @Test
    fun testAddButtonNavigatesToAddEntryActivity() {
        val scenario = ActivityScenario.launch(CalendarActivity::class.java)
        scenario.use {
            // Click on the "Add" button
            onView(withId(R.id.addButton)).perform(click())

            // Checking that AddEntryActivity is open
            Intents.intended(hasComponent(AddEntryActivity::class.java.name))
        }
    }
}
