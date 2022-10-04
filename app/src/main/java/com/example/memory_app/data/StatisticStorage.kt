package com.example.memory_app.data

import android.util.Log
import com.example.memory_app.domain.repository.Score
import com.example.memory_app.domain.repository.Statistic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface StatisticStorage {
    fun getStatistic() : Flow<Statistic>
    suspend fun updateStatistic()
}

class StatisticStorageTestImpl() : StatisticStorage {
    override fun getStatistic(): Flow<Statistic> = flow {
            emit(Statistic(Score(0), 0))
        }

    override suspend fun updateStatistic() {
        Log.d("updateStatistic", "Updated!")
    }
}