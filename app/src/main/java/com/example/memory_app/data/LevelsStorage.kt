package com.example.memory_app.data

import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.Level
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface LevelsStorage {
    fun getAllLevels(): Flow<List<Level>>
    suspend fun getLevel(name: String): Level
}

class LevelsStorageTestImpl() : LevelsStorage {

    private val levels : Map<String, Level> = hashMapOf(
        "Test1" to Level("Test1", Difficulty.EASY, listOf(0, 1, 2)),
        "Test2" to Level("Test2", Difficulty.EASY, listOf(10, 11, 12)),
        "Test3" to Level("Test3", Difficulty.MEDIUM, listOf(100, 101, 102, 103, 104, 105))
    )

    override fun getAllLevels(): Flow<List<Level>> = flow {
        emit(
            levels.map { it.value }.toList()
        )
    }

    override suspend fun getLevel(name: String): Level = levels[name]!!
}