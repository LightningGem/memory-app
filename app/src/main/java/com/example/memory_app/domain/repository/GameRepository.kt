package com.example.memory_app.domain.repository

import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun getLevel(name : String) : Level
    fun getAllLevels() : Flow<List<Level>>
    fun getStatistic() : Flow<Statistic>
    suspend fun updateStatistic(score : Score)
}

