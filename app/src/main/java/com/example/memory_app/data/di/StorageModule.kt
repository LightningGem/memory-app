package com.example.memory_app.data.di

import com.example.memory_app.data.levels.LevelsSource
import com.example.memory_app.data.levels.LevelsSourceImpl
import com.example.memory_app.data.levels.resources.LevelsResourcesSource
import com.example.memory_app.data.levels.resources.LevelsResourcesSourceImpl
import com.example.memory_app.data.statistic.StatisticSource
import com.example.memory_app.data.statistic.StatisticSourceImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
    @Singleton
    @Binds
    abstract fun bindLevelsSource(levelsSourceImpl: LevelsSourceImpl) : LevelsSource

    @Singleton
    @Binds
    abstract fun bindStatisticSource(statisticSourceImpl: StatisticSourceImpl) : StatisticSource

    @Singleton
    @Binds
    abstract fun bindResourcesHolder(levelsResourcesSourceImpl: LevelsResourcesSourceImpl) : LevelsResourcesSource
}