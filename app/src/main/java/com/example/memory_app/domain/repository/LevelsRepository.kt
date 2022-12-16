package com.example.memory_app.domain.repository

import com.example.memory_app.domain.entities.Level
import kotlinx.coroutines.flow.Flow

interface LevelsRepository {
    fun getLevel(name : String) : Level
    fun getAllLevels(remote : Boolean) : Flow<List<Level>>
}