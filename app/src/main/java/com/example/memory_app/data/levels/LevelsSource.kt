package com.example.memory_app.data.levels

import com.example.memory_app.domain.entities.Level
import kotlinx.coroutines.flow.Flow

interface LevelsSource {
    fun getAllLevels(remote : Boolean): Flow<List<Level>>
    fun getLevel(name: String): Level
}