package com.example.memory_app.data.levels.resources

import com.example.memory_app.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalLevelResourcesHolder @Inject constructor() : LevelResourcesHolder {
    private val resources : List<Resources> = listOf(
        Resources("Test Level", listOf(
            R.drawable.test_level_card1,
            R.drawable.test_level_card2,
            R.drawable.test_level_card3,
            R.drawable.test_level_icon,
            R.drawable.test_level_icon
        )),
        Resources("Test Level2", listOf(
            R.drawable.test_level_card1,
            R.drawable.test_level_card2,
            R.drawable.test_level_card3,
            R.drawable.test_level_icon,
            R.drawable.test_level_card1
        ))

    )

    override fun getLevelResources(levelName : String) : Resources =
        resources.first{ it.levelName == levelName}

    override fun getAllLevelResources() : List<Resources> = resources
}