package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.repository.GameRepository
import com.example.memory_app.domain.repository.Statistic
import kotlinx.coroutines.flow.Flow

class LoadStatisticUseCase(private val repository: GameRepository) {
    operator fun invoke() : Flow<Statistic> = repository.getStatistic()
}
