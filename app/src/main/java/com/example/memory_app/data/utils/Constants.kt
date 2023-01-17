package com.example.memory_app.data.utils

import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

object Constants {
    // Firestore
    const val COLLECTION_PATH = "levels"
    const val KEY_CARDS_URIS = "cardImagesUris"
    const val KEY_DIFFICULTY = "difficulty"
    const val KEY_FACE_OFF_URI = "faceOffImageUri"
    const val KEY_ICON_URI = "levelIconImageUri"

    // SharedPreferences
    val AVERAGE_SCORE = doublePreferencesKey("averageScore")
    val LEVELS_COMPLETED = intPreferencesKey("levelsCompleted")
    const val USER_PREFERENCES = "memory-app"
}