package com.example.memory_app.domain.use_cases

import android.content.Context
import com.example.memory_app.R
import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.GameRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadLevelsInfoUseCase @Inject constructor
    (private val repository: GameRepository, @ApplicationContext private val context: Context) {

    private val statisticFlow = repository.getStatistic()

    operator fun invoke(remote : Boolean) = statisticFlow.flatMapLatest { statistic ->
        repository.getAllLevels(remote).map { levels ->
            levels
                .filter { level -> predicate(level.difficulty, statistic.levelsCompleted) }
                .map { level -> Pair(level.name, level.difficulty) }
        }
    }

    private fun predicate(difficulty: Difficulty, levelsCompleted : Int) : Boolean {
        return when (difficulty) {
            Difficulty.EASY -> true
            Difficulty.MEDIUM -> (levelsCompleted >= context.resources.getInteger(R.integer.medium))
            Difficulty.HARD -> (levelsCompleted >= context.resources.getInteger(R.integer.hard))
        }
    }
}