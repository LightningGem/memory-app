package com.example.memory_app.data.levels.resources

interface LevelResourcesHolder {
    fun getLevelResources(levelName : String) : Resources
    fun getAllLevelResources() : List<Resources>
}