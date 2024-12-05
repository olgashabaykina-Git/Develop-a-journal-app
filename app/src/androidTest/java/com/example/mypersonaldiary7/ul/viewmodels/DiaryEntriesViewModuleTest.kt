package com.example.mypersonaldiary7.ul.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.room.Room
import com.example.mypersonaldiary7.MainApplication
import com.example.mypersonaldiary7.data.model.DiaryEntries
import com.example.mypersonaldiary7.data.database.DiaryEntriesDatabase
import com.example.mypersonaldiary7.utils.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class DiaryEntriesViewModelIntegrationTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DiaryEntriesDatabase
    private lateinit var viewModel: DiaryEntriesViewModel

    @Before
    fun setUp() {
        // Creating an in-memory database for tests
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, DiaryEntriesDatabase::class.java)
            .allowMainThreadQueries() // allow queries in the main thread (for tests only)
            .build()

        // "Imitation" MainApplication
        MainApplication.diaryEntriesDatabase = database

        // Creating a ViewModel
        viewModel = DiaryEntriesViewModel()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testAddDiaryEntry() = runTest {
        val entry = DiaryEntries(0, "Test Entry", Date(), null)

        // Adding an entry
        viewModel.addDiaryEntry(entry)

        // get all the records
        val entries = database.getDiaryEntriesDao().getAllDiaryEntries().getOrAwaitValue()

        // Checking the result
        Assert.assertEquals(1, entries.size)
        Assert.assertEquals("Test Entry", entries[0].title)
    }

    @Test
    fun testDeleteDiaryEntry() = runTest {
        val entry = DiaryEntries(0, "Test Entry", Date(), null)

        // Adding an entry
        viewModel.addDiaryEntry(entry)

        // Deleting an entry
        val addedEntry = database.getDiaryEntriesDao().getAllDiaryEntries().getOrAwaitValue()[0]
        viewModel.deleteDiaryEntry(addedEntry.id)

        // check that the record has been deleted
        val entries = database.getDiaryEntriesDao().getAllDiaryEntries().getOrAwaitValue()
        Assert.assertTrue(entries.isEmpty())
    }

    @Test
    fun testUpdateDiaryEntry() = runTest {
        val entry = DiaryEntries(0, "Initial Title", Date(), null)

        // Adding an entry
        viewModel.addDiaryEntry(entry)

        // Updating the record
        val addedEntry = database.getDiaryEntriesDao().getAllDiaryEntries().getOrAwaitValue()[0]
        val updatedEntry = addedEntry.copy(title = "Updated Title")
        viewModel.updateDiaryEntry(updatedEntry)

        // check that the record has been updated
        val entries = database.getDiaryEntriesDao().getAllDiaryEntries().getOrAwaitValue()
        Assert.assertEquals("Updated Title", entries[0].title)
    }

    @Test
    fun testGetAllDiaryEntries() = runTest {
        val entry1 = DiaryEntries(0, "Entry 1", Date(), null)
        val entry2 = DiaryEntries(0, "Entry 2", Date(), null)

        // Adding entries
        viewModel.addDiaryEntry(entry1)
        viewModel.addDiaryEntry(entry2)

        // get all the records
        val entries = database.getDiaryEntriesDao().getAllDiaryEntries().getOrAwaitValue()

        // Checking the result
        Assert.assertEquals(2, entries.size)
        Assert.assertEquals("Entry 1", entries[0].title)
        Assert.assertEquals("Entry 2", entries[1].title)
    }
}
