package com.example.memory_app.domain.di

import com.example.memory_app.data.LevelsRepositoryImpl
import com.example.memory_app.data.StatisticRepositoryImpl
import com.example.memory_app.domain.repository.LevelsRepository
import com.example.memory_app.domain.repository.StatisticRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
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

    companion object {
        @Provides
        @Named("IO")
        fun providesDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        @Named("Default")
        fun providesDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

        @Provides
        @Named("Main")
        fun providesDispatcherMain(): CoroutineDispatcher = Dispatchers.Main
    }
}