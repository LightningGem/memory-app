package com.example.memory_app.data

import com.example.memory_app.domain.repository.GameRepository
import com.example.memory_app.domain.repository.Level
import com.example.memory_app.domain.repository.Score
import com.example.memory_app.domain.repository.Statistic
import kotlinx.coroutines.flow.Flow

class GameRepositoryImpl(private val statisticStorage: StatisticStorage,
                         private val levelsStorage: LevelsStorage) : GameRepository {

    override suspend fun getLevel(name: String): Level = levelsStorage.getLevel(name)

    override fun getAllLevels(): Flow<List<Level>> = levelsStorage.getAllLevels()

    override fun getStatistic(): Flow<Statistic> = statisticStorage.getStatistic()

    override suspend fun updateStatistic(score: Score) = statisticStorage.updateStatistic()
}

