package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.model.Game
import com.example.memory_app.domain.model.GameFactory
import com.example.memory_app.domain.repository.LevelsRepository
import javax.inject.Inject

class LoadLevelUseCase @Inject constructor(private val levelsRepository: LevelsRepository,
                                           private val gameFactory : GameFactory) {
    operator fun invoke(levelName : String) : Game {
        val level = levelsRepository.getLevel(levelName)
        return gameFactory.startNewGame(level.CardIds, level.difficulty)
    }
}