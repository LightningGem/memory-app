package com.example.memory_app.domain.repository

import com.example.memory_app.domain.entities.Level
import com.example.memory_app.domain.model.Source
import kotlinx.coroutines.flow.Flow

interface LevelsRepository {
    fun getLevel(name : String) : Level
    fun getAllLevels(source: Source) : Flow<List<Level>>
}