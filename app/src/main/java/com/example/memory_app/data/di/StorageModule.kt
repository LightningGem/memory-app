package com.example.memory_app.data.di

import com.example.memory_app.data.levels.LevelsStorage
import com.example.memory_app.data.levels.LevelsStorageImpl
import com.example.memory_app.data.levels.resources.LevelsResourcesHolder
import com.example.memory_app.data.levels.resources.LevelsResourcesHolderImpl
import com.example.memory_app.data.statistic.StatisticStorage
import com.example.memory_app.data.statistic.StatisticStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
    @Binds
    abstract fun bindLevelsStorage(levelsStorageImpl: LevelsStorageImpl) : LevelsStorage

    @Binds
    abstract fun bindStatisticStorage(statisticStorageImpl: StatisticStorageImpl) : StatisticStorage

    @Binds
    abstract fun bindResourcesHolder(levelsResourcesHolderImpl: LevelsResourcesHolderImpl) : LevelsResourcesHolder
}