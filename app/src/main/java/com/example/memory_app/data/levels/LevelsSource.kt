package com.example.memory_app.data.levels

import com.example.memory_app.domain.entities.Level
import com.example.memory_app.domain.model.Source
import kotlinx.coroutines.flow.Flow

interface LevelsSource {
    fun getAllLevels(source : Source): Flow<List<Level>>
    fun getLevel(name: String): Level
}