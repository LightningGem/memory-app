package com.example.memory_app.data.levels

import com.example.memory_app.data.levels.resources.LevelsResourcesSource
import com.example.memory_app.data.levels.resources.Resources
import com.example.memory_app.domain.model.BoardGeneratorImpl
import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.entities.Level
import com.example.memory_app.presentation.gamescreen.view.CardsAdapter
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LevelsSourceImpl @Inject constructor
    (private val resourcesHolder : LevelsResourcesSource) : LevelsSource {
    /**
    CardId in View layer would associate with its image like this : imageUris.get(CardId)
    (check [CardsAdapter]). Board generation depends on [Difficulty] and list of CardId`s
    but board can be generated with only 1 CardId (check [BoardGeneratorImpl]).
    Best approach would be getting the most number of CardId limited only to how many unique
    images we have so each new game of the same [Level] we might get new images if number of
    them is more then NumberOfCards/cardsInRow of current [Difficulty].
     */
    private fun List<Resources>.toListOfLevels() = this.map { resources ->
        Level( resources.levelName,
            Difficulty.values()[resources.difficulty],
            List(resources.cardImagesUris.size) { index -> index }
        )
    }

    private lateinit var levels : List<Level>

    override fun getLevel(name: String): Level = levels.first { it.name == name }

    override fun getAllLevels(remote : Boolean): Flow<List<Level>> = flow {
          resourcesHolder.getAllLevelsResources(remote).collect {
              levels = it.toListOfLevels()
              emit(levels)
          }
    }
}