package com.example.memory_app.domain.repository

import com.example.memory_app.domain.entities.Score
import com.example.memory_app.domain.entities.Statistic
import kotlinx.coroutines.flow.Flow

interface StatisticRepository {
    fun getStatistic() : Flow<Statistic>
    suspend fun updateStatistic(score : Score)
}