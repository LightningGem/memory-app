package com.example.memory_app.domain.model

interface GameFactory {
    fun startNewGame(difficulty: Difficulty, cardsIds: List<Int>) : Game
}

class GameFactoryImpl(private val boardGenerator : RandomCardsGenerator) : GameFactory {
    override fun startNewGame(difficulty: Difficulty, cardsIds: List<Int>): Game =
        GameImpl(boardGenerator.generateListOfCards(difficulty, cardsIds), difficulty)
}