package com.example.memory_app.data.levels.resources

import com.example.memory_app.domain.model.Source
import kotlinx.coroutines.flow.Flow

interface LevelsResourcesSource {
    fun getLevelResources(levelName : String) : Resources
    fun getAllLevelsResources(source: Source) : Flow<List<Resources>>
}