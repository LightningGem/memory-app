package com.example.memory_app.data

import android.content.Context
import com.example.memory_app.R
import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.ConstraintsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ConstraintsRepositoryImpl @Inject constructor
    (@ApplicationContext private val context: Context) : ConstraintsRepository {
    override fun getMinNumberOfWinsToUnlock(difficulty: Difficulty) : Int {
        return when (difficulty) {
            Difficulty.EASY -> 0
            Difficulty.MEDIUM -> context.resources.getInteger(R.integer.medium)
            Difficulty.HARD -> context.resources.getInteger(R.integer.hard)
        }
    }
}