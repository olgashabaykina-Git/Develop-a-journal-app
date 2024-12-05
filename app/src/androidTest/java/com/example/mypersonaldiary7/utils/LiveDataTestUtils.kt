package com.example.mypersonaldiary7.utils
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

// The extension method for Live Data
fun <T> LiveData<T>.getOrAwaitValue(): T {
    var data: T? = null
    val latch = CountDownLatch(1)

    // Creating an Observer object
    val observer = object : Observer<T> {
        override fun onChanged(value: T) { // Fixed the signature of the method
            data = value
            latch.countDown() // Reducing the counter
            this@getOrAwaitValue.removeObserver(this) // watching
        }
    }

    // Watching LiveData
    this.observeForever(observer)

    try {
        // Waiting for the timeout value
        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw TimeoutException("LiveData value was not set within the timeout.")
        }
    } finally {
        // Removing the observer after completion
        this.removeObserver(observer)
    }

    // Returning the received value
    return data ?: throw NullPointerException("LiveData value was null")
}
