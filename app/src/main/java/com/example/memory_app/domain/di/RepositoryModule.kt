package com.example.memory_app.domain.di

import com.example.memory_app.data.ConstraintsRepositoryImpl
import com.example.memory_app.data.LevelsRepositoryImpl
import com.example.memory_app.data.StatisticRepositoryImpl
import com.example.memory_app.domain.repository.ConstraintsRepository
import com.example.memory_app.domain.repository.LevelsRepository
import com.example.memory_app.domain.repository.StatisticRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindStatisticRepository(statisticRepositoryImpl: StatisticRepositoryImpl): StatisticRepository

    @Singleton
    @Binds
    abstract fun bindLevelsRepository(levelsRepositoryImpl: LevelsRepositoryImpl): LevelsRepository

    @Singleton
    @Binds
    abstract fun bindConstraintsRepository(constraintsRepository: ConstraintsRepositoryImpl) : ConstraintsRepository
}