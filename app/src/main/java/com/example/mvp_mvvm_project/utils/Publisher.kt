package com.example.mvp_mvvm_project.utils

import android.os.Handler

private data class Subscriber<T>(
    private val handler: Handler,
    private val callback: (T) -> Unit
) {
    fun invoke(value: T) {
        handler.post {
            callback.invoke(value)
        }
    }
}

class Publisher<T> {
    private val subscribers: MutableSet<Subscriber<T>> = mutableSetOf()
    fun subscribe(handler: Handler, callback: (T) -> Unit) {
        val subscriber = Subscriber(handler, callback)
        subscribers.add(subscriber)
    }

    fun postValue(value: T) {
        subscribers.forEach {
            it.invoke(value)
        }
    }

    fun unsubscribeAll() {
        subscribers.clear()
    }
}
