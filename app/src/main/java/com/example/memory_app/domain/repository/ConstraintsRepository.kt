package com.example.memory_app.domain.repository

import com.example.memory_app.domain.model.Difficulty

interface ConstraintsRepository {
    fun getMinNumberOfWinsToUnlock(difficulty : Difficulty) : Int
}