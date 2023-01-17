package com.example.memory_app.data.levels

import com.example.memory_app.data.levels.resources.LevelsResourcesSource
import com.example.memory_app.data.levels.resources.Resources
import com.example.memory_app.domain.model.BoardGeneratorImpl
import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.entities.Level
import com.example.memory_app.domain.model.Source
import com.example.memory_app.presentation.gamescreen.view.CardsAdapter
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LevelsSourceImpl @Inject constructor
    (private val resourcesHolder : LevelsResourcesSource) : LevelsSource {

    /**
     * CardId in View layer would associate with its image like this : imageUris.get(CardId)
     * (check [CardsAdapter]). Board generation depends on [Difficulty] and list of CardId`s
     * but board can be generated with only 1 CardId (check [BoardGeneratorImpl]).
     * The best approach is to get the most number of CardId limited only to how many unique
     * images we have so each new game of the same [Level] we might get new images if number
     * of them is more then NumberOfCards/cardsInRow of current [Difficulty].
     **/
    private fun Resources.toLevel() = Level(
        levelName,
        Difficulty.values()[difficulty],
        List(cardImagesUris.size) { index -> index }
    )

    private fun List<Resources>.toListOfLevels() = this.map { resources -> resources.toLevel() }

    override fun getLevel(name: String): Level = resourcesHolder.getLevelResources(name).toLevel()

    override fun getAllLevels(source: Source): Flow<List<Level>> = flow {
          resourcesHolder.getAllLevelsResources(source).collect { resources ->
              emit(resources.toListOfLevels())
          }
    }
}