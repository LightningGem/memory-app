package com.example.memory_app.domain.repository

import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getAllLevels() : Flow<List<Level>>
    suspend fun getLevel(name : String) : Level
    fun getStatistic() : Flow<Statistic>
    suspend fun updateStatistic(score : Score)
}

