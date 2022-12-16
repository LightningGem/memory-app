package com.example.memory_app.data.statistic

import com.example.memory_app.domain.entities.Statistic
import kotlinx.coroutines.flow.Flow

interface StatisticSource {
    fun getStatistic() : Flow<Statistic>
    suspend fun updateStatistic(statistic: Statistic)
}