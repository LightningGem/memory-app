package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.entities.Score
import com.example.memory_app.domain.repository.LevelsRepository
import com.example.memory_app.domain.repository.StatisticRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class SaveGameResultUseCase @Inject constructor
    (private val statisticRepository: StatisticRepository,
     private val levelsRepository: LevelsRepository) {

    suspend operator fun invoke(mismatchedTimes : Int, levelName: String) : Score  {
        val difficulty = levelsRepository.getLevel(levelName).difficulty
        val score = calculateScore(mismatchedTimes, difficulty)
        statisticRepository.updateStatistic(score)
        return score
    }

    private fun calculateScore(mismatchedTimes : Int, difficulty: Difficulty) =
        Score((difficulty.mismatchAllowed - mismatchedTimes).toDouble())
}