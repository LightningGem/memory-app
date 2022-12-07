package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.repository.GameRepository
import com.example.memory_app.domain.entities.Statistic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class LoadStatisticUseCase @Inject constructor
    (private val repository: GameRepository,
     @Named("IO") private val dispatcher: CoroutineDispatcher) {
    operator fun invoke() : Flow<Statistic> = repository.getStatistic().flowOn(dispatcher)
}
