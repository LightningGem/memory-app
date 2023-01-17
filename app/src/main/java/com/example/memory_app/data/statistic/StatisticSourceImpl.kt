package com.example.memory_app.data.statistic

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.memory_app.data.utils.Constants.AVERAGE_SCORE
import com.example.memory_app.data.utils.Constants.LEVELS_COMPLETED
import com.example.memory_app.domain.entities.Score
import com.example.memory_app.domain.entities.Statistic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StatisticSourceImpl @Inject constructor
    (private val dataStore : DataStore<Preferences>) : StatisticSource {

    override fun getStatistic(): Flow<Statistic> = dataStore.data.map { preferences ->
        val averageScore : Double = preferences[AVERAGE_SCORE] ?: 0.0
        val levelsPlayed : Int = preferences[LEVELS_COMPLETED] ?: 0
        Statistic(Score(averageScore), levelsPlayed)
    }

    override suspend fun updateStatistic(statistic: Statistic) {
        dataStore.edit { preferences ->
            preferences[AVERAGE_SCORE] = statistic.averageScore.value
            preferences[LEVELS_COMPLETED] = statistic.levelsCompleted
        }

    }
}