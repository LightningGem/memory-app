package com.example.memory_app.domain.di

import com.example.memory_app.data.GameRepositoryImpl
import com.example.memory_app.domain.repository.GameRepository
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
    abstract fun bindGameRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository

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