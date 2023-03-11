package com.nong.amazingnews

class Event<out T>(private val content: T) {
    var hasHandled = false

    fun getContent(): T? {
        return when (hasHandled) {
            true -> null
            false -> {
                hasHandled = true
                content
            }
        }
    }

    fun peekContent(): T = content
}