package com.example.memory_app.data

import com.example.memory_app.data.statistic.StatisticSource
import com.example.memory_app.domain.entities.Score
import com.example.memory_app.domain.entities.Statistic
import com.example.memory_app.domain.repository.StatisticRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor
    (private val statisticSource : StatisticSource) : StatisticRepository {

    override fun getStatistic(): Flow<Statistic> = statisticSource.getStatistic()

    override suspend fun updateStatistic(score: Score) {
        val (averageScore, levelsCompleted) = getStatistic().first()
        val newAverageScore = ((averageScore.value * levelsCompleted) + score.value) / (levelsCompleted + 1)
        val newStatistic = Statistic(Score(newAverageScore), levelsCompleted + 1)
        statisticSource.updateStatistic(newStatistic)
    }
}
