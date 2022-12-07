package com.example.memory_app.domain.repository

import com.example.memory_app.domain.entities.Level
import com.example.memory_app.domain.entities.Score
import com.example.memory_app.domain.entities.Statistic
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getLevel(name : String) : Level
    fun getAllLevels(remote : Boolean) : Flow<List<Level>>
    fun getStatistic() : Flow<Statistic>
    suspend fun updateStatistic(score : Score)
}

