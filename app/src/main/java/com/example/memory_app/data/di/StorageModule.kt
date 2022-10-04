package com.example.memory_app.data.di

import com.example.memory_app.data.LevelsStorage
import com.example.memory_app.data.LevelsStorageTestImpl
import com.example.memory_app.data.StatisticStorage
import com.example.memory_app.data.StatisticStorageTestImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
    @Binds
    abstract fun bindLevelsStorage(levelsStorageImpl: LevelsStorageTestImpl) : LevelsStorage

    @Binds
    abstract fun bindStatisticStorage(statisticStorageImpl: StatisticStorageTestImpl) : StatisticStorage
}