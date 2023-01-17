package com.example.memory_app.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.memory_app.data.levels.LevelsSource
import com.example.memory_app.data.levels.LevelsSourceImpl
import com.example.memory_app.data.levels.resources.LevelsResourcesSource
import com.example.memory_app.data.levels.resources.LevelsResourcesSourceImpl
import com.example.memory_app.data.statistic.StatisticSource
import com.example.memory_app.data.statistic.StatisticSourceImpl
import com.example.memory_app.data.utils.Constants.USER_PREFERENCES

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
    @Singleton
    @Binds
    abstract fun bindStatisticSource(statisticSourceImpl: StatisticSourceImpl) : StatisticSource

    @Singleton
    @Binds
    abstract fun bindLevelsSource(levelsSourceImpl: LevelsSourceImpl) : LevelsSource

    @Singleton
    @Binds
    abstract fun bindResourcesHolder(levelsResourcesSourceImpl: LevelsResourcesSourceImpl) : LevelsResourcesSource


    companion object {
        @Singleton
        @Provides
        fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
            )
        }
    }
}