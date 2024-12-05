package com.example.mypersonaldiary7.data.converters

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class ConvertersTest {

    private lateinit var converters: Convertes

    @Before
    fun setUp() {
        converters = Convertes()
    }

    @Test
    fun `fromDate converts Date to Long correctly`() {
        // Given
        val date = Date(1698492000000L) // Example date: 28 Oct 2023, 12:00 PM UTC

        // When
        val result = converters.fromDate(date)

        // Then
        assertEquals(date.time, result)
    }

    @Test
    fun `toDate converts Long to Date correctly`() {
        // Given
        val time = 1698492000000L // Example timestamp: 28 Oct 2023, 12:00 PM UTC

        // When
        val result = converters.toDate(time)

        // Then
        assertEquals(Date(time), result)
    }
}
