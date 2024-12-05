package com.example.mypersonaldiary7.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingResource

class RecyclerViewIdlingResource(
    private val recyclerView: RecyclerView
) : IdlingResource {

    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return RecyclerViewIdlingResource::class.java.name
    }

    override fun isIdleNow(): Boolean {
        val isIdle = recyclerView.adapter?.itemCount ?: 0 > 0
        if (isIdle) {
            callback?.onTransitionToIdle() //notify Espresso that the resource is free
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }
}
