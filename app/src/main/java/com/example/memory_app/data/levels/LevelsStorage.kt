package com.example.memory_app.data.levels

import com.example.memory_app.domain.repository.Level
import kotlinx.coroutines.flow.Flow

interface LevelsStorage {
    fun getAllLevels(): Flow<List<Level>>
    fun getLevel(name: String): Level
}