package com.example.memory_app.data.levels.resources

import kotlinx.coroutines.flow.Flow

interface LevelsResourcesSource {
    fun getLevelResources(levelName : String) : Resources
    fun getAllLevelsResources(remote : Boolean) : Flow<List<Resources>>
}