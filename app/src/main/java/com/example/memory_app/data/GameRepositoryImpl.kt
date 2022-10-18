package com.example.memory_app.data

import com.example.memory_app.data.levels.LevelsStorage
import com.example.memory_app.data.statistic.StatisticStorage
import com.example.memory_app.domain.repository.GameRepository
import com.example.memory_app.domain.repository.Level
import com.example.memory_app.domain.repository.Score
import com.example.memory_app.domain.repository.Statistic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor
    (private val statisticStorage: StatisticStorage,
     private val levelsStorage: LevelsStorage
) : GameRepository {

    override fun getLevel(name: String): Level = levelsStorage.getLevel(name)

    override fun getAllLevels(remote : Boolean): Flow<List<Level>> = levelsStorage.getAllLevels(remote)

    override fun getStatistic(): Flow<Statistic> = statisticStorage.getStatistic()

    override suspend fun updateStatistic(score: Score) {
        val (averageScore, levelsCompleted) = getStatistic().first()
        val newAverageScore = ((averageScore.value * levelsCompleted) + score.value) / (levelsCompleted + 1)
        val newStatistic = Statistic(Score(newAverageScore), levelsCompleted + 1)
        statisticStorage.updateStatistic(newStatistic)
    }
}

