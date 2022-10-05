package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.GameRepository
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadLevelsInfoUseCase @Inject constructor(private val repository: GameRepository) {

    private val statisticFlow = repository.getStatistic()

    operator fun invoke() = statisticFlow.flatMapLatest { statistic ->
        repository.getAllLevels().map { levels ->
            levels
                .filter { level -> predicate(level.difficulty, statistic.levelsCompleted) }
                .map { level -> Pair(level.name, level.difficulty) }
        }
    }

    private fun predicate(difficulty: Difficulty, levelsCompleted : Int) : Boolean {
        return when (difficulty) {
            Difficulty.EASY -> true
            Difficulty.MEDIUM -> (levelsCompleted > 10)
            else -> (levelsCompleted > 20)
        }
    }
}