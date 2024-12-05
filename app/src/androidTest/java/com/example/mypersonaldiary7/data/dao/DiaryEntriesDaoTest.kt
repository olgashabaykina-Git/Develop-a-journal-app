package com.example.mypersonaldiary7.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mypersonaldiary7.data.database.DiaryEntriesDatabase
import com.example.mypersonaldiary7.data.model.DiaryEntries
import com.example.mypersonaldiary7.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class DiaryEntriesDaoTest {

    // InstantTaskExecutorRule to work with LiveData in tests
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DiaryEntriesDatabase
    private lateinit var dao: DiaryEntriesDao

    @Before
    fun setup() {
        // Creating an in-memory database that exists only during test execution
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DiaryEntriesDatabase::class.java
        ).allowMainThreadQueries() //  allow the execution of requests on the main thread for testing
            .build()
        dao = database.getDiaryEntriesDao()
    }

    @After
    fun teardown() {
        // Closing the database after completing all the tests
        database.close()
    }

    @Test
    fun testAddAndRetrieveDiaryEntry() = runBlocking {
        // Creating a test record
        val diaryEntry = DiaryEntries(1, "Test Entry", Date(), "sampleUri")

        // Add a record and extract all records from the database
        dao.addDiaryEntries(diaryEntry)
        val retrievedEntries = dao.getAllDiaryEntries().getOrAwaitValue()

        //  check that the added record is being extracted correctly
        Assert.assertEquals(1, retrievedEntries.size) //Checking the size of the list
        Assert.assertEquals("Test Entry", retrievedEntries[0].title) // Checking the title
        Assert.assertEquals("sampleUri", retrievedEntries[0].imageUri) //Checking the image URL
    }

    @Test
    fun testDeleteDiaryEntry() = runBlocking {
        //Adding an entry
        val diaryEntry = DiaryEntries(1, "Test Entry", Date(), "sampleUri")
        dao.addDiaryEntries(diaryEntry)

        // delete the record and get an updated list of records
        dao.deleteDiaryEntry(1)
        val retrievedEntries = dao.getAllDiaryEntries().getOrAwaitValue()

        // check that the list of records is empty after deletion
        Assert.assertTrue(retrievedEntries.isEmpty())
    }

    @Test
    fun testUpdateDiaryEntry() = runBlocking {
        //  Adding an entry
        val diaryEntry = DiaryEntries(1, "Test Entry", Date(), "sampleUri")
        dao.addDiaryEntries(diaryEntry)

        // update the record and extract it again
        val updatedEntry = DiaryEntries(1, "Updated Entry", Date(), "updatedUri")
        dao.updateDiaryEntry(updatedEntry)
        val retrievedEntries = dao.getAllDiaryEntries().getOrAwaitValue()

        // check that the record data has been updated
        Assert.assertEquals(1, retrievedEntries.size) //
        Assert.assertEquals("Updated Entry", retrievedEntries[0].title)
        Assert.assertEquals("updatedUri", retrievedEntries[0].imageUri)
    }
}
