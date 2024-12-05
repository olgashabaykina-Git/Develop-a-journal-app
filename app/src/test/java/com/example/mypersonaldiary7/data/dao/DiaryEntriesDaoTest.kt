package com.example.mypersonaldiary7.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.mypersonaldiary7.data.model.DiaryEntries
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.junit.Assert.*

import getOrAwaitValue


class DiaryEntriesDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Disables checks on the main thread

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var dao: DiaryEntriesDao

    private lateinit var mockLiveData: MutableLiveData<List<DiaryEntries>>

    @Before
    fun setUp() {
        // Initializing Live Data
        mockLiveData = MutableLiveData()

        // Using the mock method to return mockLiveData
        `when`(dao.getAllDiaryEntries()).thenReturn(mockLiveData)
    }

    @Test
    fun getAllDiaryEntries() {
        // Preparation of test data
        val entries = listOf(
            DiaryEntries(title = "Entry 1", createdAt = java.util.Date(), imageUri = "uri1"),
            DiaryEntries(title = "Entry 2", createdAt = java.util.Date(), imageUri = "uri2")
        )

        // Installing the data in Live Data
        mockLiveData.value = entries

        // Getting the result from Live Data
        val result = dao.getAllDiaryEntries().getOrAwaitValue() // Используем getOrAwaitValue

        // check that the result is not null
        assertNotNull(result)

        // check the size of the list and the correctness of the values
        assertEquals(2, result.size)
        assertEquals("Entry 1", result[0].title)
    }
}
