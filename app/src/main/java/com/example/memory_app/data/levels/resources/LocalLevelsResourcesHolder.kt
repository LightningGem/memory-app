package com.example.memory_app.data.levels.resources

import com.example.memory_app.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalLevelsResourcesHolder @Inject constructor() : LevelsResourcesHolder {
    private val resources : List<Resources> = listOf(
        Resources("Basics", listOf(
            R.drawable.test_card1,
            R.drawable.test_card2,
            R.drawable.test_card3),
            R.drawable.face_off_image,
            R.drawable.test_card1),
        Resources("Furniture", listOf(
            R.drawable.test_card4,
            R.drawable.test_card5,
            R.drawable.test_card6,
            R.drawable.test_card7,
            R.drawable.test_card8),
            R.drawable.face_off_image,
            R.drawable.test_icon1),
        Resources("Sport", listOf(
            R.drawable.test_card9,
            R.drawable.test_card10,
            R.drawable.test_card11,
            R.drawable.test_card12,
            R.drawable.test_card13,
            R.drawable.test_card14),
            R.drawable.face_off_image,
            R.drawable.test_icon2)
    )

    override fun getLevelResources(levelName : String) : Resources =
        resources.first{ it.levelName == levelName }

    override fun getAllLevelsResources() : List<Resources> = resources
}