package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.repository.GameRepository
import com.example.memory_app.domain.repository.Statistic
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadStatisticUseCase @Inject constructor(private val repository: GameRepository) {
    operator fun invoke() : Flow<Statistic> = repository.getStatistic()
}
