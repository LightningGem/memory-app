package com.example.memory_app.domain.di

import com.example.memory_app.domain.model.BoardGenerator
import com.example.memory_app.domain.model.BoardGeneratorImpl
import com.example.memory_app.domain.model.GameFactory
import com.example.memory_app.domain.model.GameFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GameLogicModule {
    @Binds
    abstract fun bindGameFactory(gameFactoryImpl: GameFactoryImpl) : GameFactory

    companion object {
        @Provides
        fun providesBoardGenerator(): BoardGenerator = BoardGeneratorImpl
    }

}