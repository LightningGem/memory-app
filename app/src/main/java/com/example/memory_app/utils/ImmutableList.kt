package com.example.memory_app.utils

/**
 * Wrapper for mutable list
 * */
class ImmutableList<T> (private val protectedList: List<T>) : List<T> by protectedList