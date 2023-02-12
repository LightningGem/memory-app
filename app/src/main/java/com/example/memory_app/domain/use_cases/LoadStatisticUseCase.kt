package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.entities.Statistic
import com.example.memory_app.domain.repository.StatisticRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadStatisticUseCase @Inject constructor(private val repository: StatisticRepository) {
    operator fun invoke() : Flow<Statistic> = repository.getStatistic()
}
