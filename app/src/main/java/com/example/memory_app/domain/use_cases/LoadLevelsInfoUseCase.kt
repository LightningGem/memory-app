package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.model.Source
import com.example.memory_app.domain.repository.ConstraintsRepository
import com.example.memory_app.domain.repository.LevelsRepository
import com.example.memory_app.domain.repository.StatisticRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class LoadLevelsInfoUseCase @Inject constructor
    (statisticRepository: StatisticRepository,
     private val levelsRepository: LevelsRepository,
     private val constraintsRepository: ConstraintsRepository) {

    private val statisticFlow = statisticRepository.getStatistic()

    operator fun invoke(source: Source) = levelsRepository
        .getAllLevels(source)
        .combine(statisticFlow) { levels, statistic -> levels
            .filter { level -> predicate(level.difficulty, statistic.levelsCompleted) }
            .map { level -> Pair(level.name, level.difficulty) }
        }

    private fun predicate(difficulty: Difficulty, levelsCompleted : Int) : Boolean =
        levelsCompleted >= constraintsRepository.getMinNumberOfWinsToUnlock(difficulty)
}