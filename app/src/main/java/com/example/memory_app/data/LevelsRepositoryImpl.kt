package com.example.memory_app.data

import com.example.memory_app.data.levels.LevelsSource
import com.example.memory_app.domain.entities.Level
import com.example.memory_app.domain.repository.LevelsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LevelsRepositoryImpl @Inject constructor
    (private val levelsSource : LevelsSource) : LevelsRepository {
    override fun getLevel(name: String): Level = levelsSource.getLevel(name)

    override fun getAllLevels(remote : Boolean): Flow<List<Level>> = levelsSource.getAllLevels(remote)
}
