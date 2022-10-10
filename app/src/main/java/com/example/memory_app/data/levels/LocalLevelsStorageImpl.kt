package com.example.memory_app.data.levels

import com.example.memory_app.data.levels.resources.LevelsResourcesHolder
import com.example.memory_app.domain.model.BoardGeneratorImpl
import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.Level
import com.example.memory_app.presentation.gamescreen.view.CardsAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalLevelsStorageImpl @Inject constructor(localResources : LevelsResourcesHolder) : LevelsStorage {

    /**
    CardId in View layer would associate with its image like this : imageUris.get(CardId)
    (check [CardsAdapter]). Board generation depends on [Difficulty] and list of CardId`s
    but board can be generated with only 1 CardId (check [BoardGeneratorImpl]).
    Best approach would be getting the most number of CardId limited only to how many unique
    images we have so each new game of the same [Level] we might get new images if number of
    them is more then NumberOfCards/cardsInRow of current [Difficulty].
     */
    private val levels = localResources.getAllLevelsResources().mapIndexed {position, resources ->
        Level( resources.levelName,
               Difficulty.values()[position % Difficulty.values().size],
               List(resources.cardImagesUris.size) { index -> index }
        )
    }

    override fun getAllLevels(): Flow<List<Level>> = flow {
        emit(levels)
    }

    override fun getLevel(name: String): Level = levels.first { it.name == name }
}