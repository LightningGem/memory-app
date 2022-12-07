package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.GameRepository
import com.example.memory_app.domain.entities.Score
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class SaveGameResultUseCase @Inject constructor
    (private val repository: GameRepository,
    @Named("IO") private val dispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(mismatchedTimes : Int, levelName: String) : Score =
        withContext(dispatcher) {
        val difficulty = repository.getLevel(levelName).difficulty
        val score = getScore(mismatchedTimes, difficulty)
        repository.updateStatistic(score)
        score
    }

    private fun getScore(mismatchedTimes : Int, difficulty: Difficulty) : Score {
        return Score((difficulty.mismatchAllowed - mismatchedTimes).toDouble())
    }
}