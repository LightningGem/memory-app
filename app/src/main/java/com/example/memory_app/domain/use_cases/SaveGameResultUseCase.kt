package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.GameRepository
import com.example.memory_app.domain.repository.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveGameResultUseCase @Inject constructor(private val repository: GameRepository) {

    suspend operator fun invoke(mismatchedTimes : Int, levelName: String) : Score {
        val difficulty = repository.getLevel(levelName).difficulty
        val score = getScore(mismatchedTimes, difficulty)
        repository.updateStatistic(score)
        return score
    }

    private fun getScore(mismatchedTimes : Int, difficulty: Difficulty) : Score {
        //    TODO("logic not implemented yet")
        return Score(mismatchedTimes.toDouble())
    }
}