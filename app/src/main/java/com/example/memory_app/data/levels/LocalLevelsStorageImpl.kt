package com.example.memory_app.data.levels

import com.example.memory_app.data.levels.resources.LevelResourcesHolder
import com.example.memory_app.domain.model.BoardGeneratorImpl
import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.Level
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalLevelsStorageImpl @Inject constructor(localResources : LevelResourcesHolder) : LevelsStorage {

    /**
    CardId in View layer would associate with its image like this : imageUris.get(CardId).
    Board generation depends on [Difficulty] and list of CardId`s but board can be generated
    with only 1 CardId (check [BoardGeneratorImpl]).
    Best approach would be getting the most number of CardId limited only to how many unique
    images we have so each new game of the same [Level] we might get new images if number of
    them is more then NumberOfCards/cardsInRow of current [Difficulty].
    Last two images in imageUris reserved for face off image and level icon in menu.
     */
    private val levels = localResources.getAllLevelResources().associate {
        it.levelName to Level(   it.levelName,
            Difficulty.EASY,
            List(it.imageUris.size - 2) { index -> index }   )
    }

    override fun getAllLevels(): Flow<List<Level>> = flow {
        emit(levels.map { it.value }.toList())
    }

    override fun getLevel(name: String): Level = levels[name]!!
}