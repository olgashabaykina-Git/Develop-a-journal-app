
package com.example.mypersonaldiary7.ul.activities

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mypersonaldiary7.R
import com.example.mypersonaldiary7.utils.RecyclerViewIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditEntryActivityTest {

    private lateinit var recyclerViewIdlingResource: RecyclerViewIdlingResource

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
        if (::recyclerViewIdlingResource.isInitialized) {
            IdlingRegistry.getInstance().unregister(recyclerViewIdlingResource)
        }
    }

    @Test
    fun testAddEntryAndOpenEditEntryActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.use {

            onView(withId(R.id.addButton)).perform(click())

            // check that AddEntryActivity has opened
            Intents.intended(hasComponent("com.example.mypersonaldiary7.ul.activities.AddEntryActivity"))

            // Entering the data of the new record
            val newEntryTitle = "Test Entry"
            onView(withId(R.id.entryEditText)).perform(typeText(newEntryTitle), closeSoftKeyboard())
            onView(withId(R.id.saveButton)).perform(click())

            // check that we are back in MainActivity
            onView(withId(R.id.addButton)).check(matches(isDisplayed()))

            // check that the new entry is displayed in the list
            onView(withText(newEntryTitle)).check(matches(isDisplayed()))

            // Click on the new entry
            onView(withText(newEntryTitle)).perform(click())

            // check that the EditEntryActivity has opened
            Intents.intended(hasComponent("com.example.mypersonaldiary7.ul.activities.EditEntryActivity"))

            // check that the data is displayed correctly
            onView(withId(R.id.entryEditText)).check(matches(withText(newEntryTitle)))
        }
    }

    @Test
    fun testEditEntryInEditEntryActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.use {
            // click on the "Add" button
            onView(withId(R.id.addButton)).perform(click())

            // Entering the data of the new record
            val entryTitle = "Original Entry"
            onView(withId(R.id.entryEditText)).perform(typeText(entryTitle), closeSoftKeyboard())
            onView(withId(R.id.saveButton)).perform(click())

            // check that we are back in MainActivity
            onView(withText(entryTitle)).check(matches(isDisplayed()))

            // Click on the record
            onView(withText(entryTitle)).perform(click())

            // check that the EditEntryActivity has opened
            Intents.intended(hasComponent("com.example.mypersonaldiary7.ul.activities.EditEntryActivity"))

            // Changing the record
            val updatedTitle = "Updated Entry"
            onView(withId(R.id.entryEditText)).perform(clearText(), typeText(updatedTitle), closeSoftKeyboard())
            onView(withId(R.id.saveButton)).perform(click())

            // check the back in MainActivity
            onView(withText(updatedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testDeleteEntryInEditEntryActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.use {
            // Click on the "Add" button
            onView(withId(R.id.addButton)).perform(click())

            // Entering the data of the new record
            val entryTitle = "Entry to Delete"
            onView(withId(R.id.entryEditText)).perform(typeText(entryTitle), closeSoftKeyboard())
            onView(withId(R.id.saveButton)).perform(click())

            // check the back in MainActivity
            onView(withText(entryTitle)).check(matches(isDisplayed()))

            // Click on the record
            onView(withText(entryTitle)).perform(click())

            // check that the EditEntryActivity has opened
            Intents.intended(hasComponent("com.example.mypersonaldiary7.ul.activities.EditEntryActivity"))

            // Deleting an entry
            onView(withId(R.id.deleteButton)).perform(click())

            // check that the record is no longer displayed
            onView(withText(entryTitle)).check(doesNotExist())
        }
    }
}
