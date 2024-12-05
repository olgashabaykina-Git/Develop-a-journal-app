import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

// Extension for getting values from Live Data
fun <T> LiveData<T>.getOrAwaitValue(): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = Observer<T> { value ->
        data = value
        latch.countDown()
    }
    observeForever(observer)

    // waiting for the value to be received
    try {
        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw IllegalStateException("LiveData value was never set.")
        }
    } finally {
        removeObserver(observer)
    }
    return data ?: throw IllegalStateException("LiveData value is null.")
}
