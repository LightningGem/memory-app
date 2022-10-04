package com.example.memory_app.domain.model

import javax.inject.Inject

interface GameFactory {
    fun startNewGame(difficulty: Difficulty, cardsIds: List<Int>) : Game
}

class GameFactoryImpl @Inject constructor(private val boardGenerator : BoardGenerator) : GameFactory {
    override fun startNewGame(difficulty: Difficulty, cardsIds: List<Int>): Game =
        GameImpl(boardGenerator.generateListOfCards(difficulty, cardsIds), difficulty)
}