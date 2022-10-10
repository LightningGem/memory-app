package com.example.memory_app.data.levels.resources

interface LevelsResourcesHolder {
    fun getLevelResources(levelName : String) : Resources
    fun getAllLevelsResources() : List<Resources>
}