package com.example.mypersonaldiary7.data.database

import com.example.mypersonaldiary7.utils.getOrAwaitValue
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mypersonaldiary7.data.dao.DiaryEntriesDao
import com.example.mypersonaldiary7.data.model.DiaryEntries
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class DiaryEntriesDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DiaryEntriesDatabase
    private lateinit var dao: DiaryEntriesDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = DiaryEntriesDatabase.getInstance(context)
        dao = database.getDiaryEntriesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testMigration() = runBlocking {
        // testing database migration
        val diaryEntry = DiaryEntries(1, "Test Entry", Date(), "sampleUri")
        dao.addDiaryEntries(diaryEntry)
        val retrievedEntries = dao.getAllDiaryEntries().getOrAwaitValue()

        Assert.assertEquals(1, retrievedEntries.size)
        Assert.assertEquals("sampleUri", retrievedEntries[0].imageUri)
    }
}
