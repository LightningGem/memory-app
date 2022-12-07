package com.example.memory_app.data.statistic

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.memory_app.data.statistic.StatisticStorageImpl.PreferencesKeys.AVERAGE_SCORE
import com.example.memory_app.data.statistic.StatisticStorageImpl.PreferencesKeys.LEVELS_COMPLETED
import com.example.memory_app.data.statistic.StatisticStorageImpl.PreferencesKeys.USER_PREFERENCES_NAME
import com.example.memory_app.domain.entities.Score
import com.example.memory_app.domain.entities.Statistic
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StatisticStorageImpl @Inject constructor
    (@ApplicationContext private val context : Context) : StatisticStorage {

    private val Context.dataStore by preferencesDataStore(USER_PREFERENCES_NAME)

    private object PreferencesKeys {
        val AVERAGE_SCORE = doublePreferencesKey("averageScore")
        val LEVELS_COMPLETED = intPreferencesKey("levelsCompleted")
        const val USER_PREFERENCES_NAME = "statistic"
    }


    override fun getStatistic(): Flow<Statistic> = context.dataStore.data.map { preferences ->
        val averageScore : Double = preferences[AVERAGE_SCORE] ?: 0.0
        val levelsPlayed : Int = preferences[LEVELS_COMPLETED] ?: 0
        Statistic(Score(averageScore), levelsPlayed)
    }


    override suspend fun updateStatistic(statistic: Statistic) {
        context.dataStore.edit { preferences ->
            preferences[AVERAGE_SCORE] = statistic.averageScore.value
            preferences[LEVELS_COMPLETED] = statistic.levelsCompleted
        }

    }
}