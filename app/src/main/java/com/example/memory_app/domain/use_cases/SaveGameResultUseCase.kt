package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.GameRepository
import com.example.memory_app.domain.repository.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SaveGameResultUseCase(private val repository: GameRepository,
                            private val scope: CoroutineScope) {

    operator fun invoke(mismatchedTimes : Int, difficulty: Difficulty) : Score {
        val score = getScore(mismatchedTimes, difficulty)
        scope.launch {
            repository.updateStatistic(score)
        }
        return score
    }

    private fun getScore(mismatchedTimes : Int, difficulty: Difficulty) : Score {
        TODO("logic not implemented yet")
    }
}