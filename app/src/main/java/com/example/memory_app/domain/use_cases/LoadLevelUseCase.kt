package com.example.memory_app.domain.use_cases

import com.example.memory_app.domain.model.Game
import com.example.memory_app.domain.model.GameFactory
import com.example.memory_app.domain.repository.GameRepository
import javax.inject.Inject

class LoadLevelUseCase @Inject constructor(private val repository: GameRepository,
                                          private val gameFactory : GameFactory) {
    suspend operator fun invoke(levelName : String) : Game {
        val level = repository.getLevel(levelName)
        return gameFactory.startNewGame(level.difficulty, level.CardIds)
    }
}

