package com.example.memory_app.domain.model

import javax.inject.Inject
import javax.inject.Singleton

interface GameFactory {
    fun startNewGame(cardsIds: List<Int>, difficulty: Difficulty) : Game
}

@Singleton
class GameFactoryImpl @Inject constructor(private val boardGenerator : BoardGenerator) : GameFactory {
    override fun startNewGame(cardsIds: List<Int>, difficulty: Difficulty): Game =
        GameImpl(boardGenerator.generateListOfCards(difficulty, cardsIds),
            difficulty.cardsInRow,
            difficulty.mismatchAllowed)
}