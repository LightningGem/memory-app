package com.example.memory_app.domain.use_cases

import android.content.Context
import com.example.memory_app.R
import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.LevelsRepository
import com.example.memory_app.domain.repository.StatisticRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadLevelsInfoUseCase @Inject constructor
    (statisticRepository: StatisticRepository,
     private val levelsRepository: LevelsRepository,
     @ApplicationContext private val context: Context) {

    private val statisticFlow = statisticRepository.getStatistic()

    operator fun invoke(remote : Boolean) = statisticFlow.flatMapLatest { statistic ->
        levelsRepository.getAllLevels(remote).map { levels ->
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