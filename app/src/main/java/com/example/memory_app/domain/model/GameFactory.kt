package com.example.memory_app.domain.model

import javax.inject.Inject

interface GameFactory {
    fun startNewGame(cardsIds: List<Int>, difficulty: Difficulty) : Game
}

class GameFactoryImpl @Inject constructor(private val boardGenerator : BoardGenerator) : GameFactory {
    override fun startNewGame(cardsIds: List<Int>, difficulty: Difficulty): Game =
        GameImpl(
            board = boardGenerator.generateListOfCards(difficulty, cardsIds),
            cardsInRow = difficulty.cardsInRow,
            mismatchAllowed = difficulty.mismatchAllowed)
}