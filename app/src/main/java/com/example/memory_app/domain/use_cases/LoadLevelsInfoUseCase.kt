package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.GameRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LoadLevelsInfoUseCase (private val repository: GameRepository) {
    operator fun invoke() = repository.getAllLevels().map { levels ->
        levels.filter { predicate(it.difficulty) }.map { level ->
            Triple(level.name, level.difficulty, level.CardIds)
        }
    }

    private suspend fun predicate(difficulty: Difficulty) : Boolean {
       val levelsCompleted = repository.getStatistic().first().levelsCompleted
        return when (difficulty) {
            Difficulty.EASY -> true
            Difficulty.MEDIUM -> (levelsCompleted > 10)
            Difficulty.HARD -> (levelsCompleted > 20)
        }
    }
}