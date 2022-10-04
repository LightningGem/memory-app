package com.example.memory_app.domain.model

import com.example.memory_app.utils.ImmutableList

interface Game {
    fun onCardClicked(position: Int): Reaction
    fun getBoard(): Reaction.Running
}

class GameImpl(
    private val board: MutableList<Card>,
    private val difficulty: Difficulty
) : Game {

    private val previouslyClickedCardsPositions: MutableList<Int> = mutableListOf()

    private var mismatchedTimes : Int = 0

    private var cardsInRowMismatched = false
        set(value) {
            if (value) mismatchedTimes += 1
            field = value
        }

    private fun isGameOver() = board.all { Card -> Card.isFaceUp }

    private fun cardIsAlreadyOpen(position: Int): Boolean = board[position].isFaceUp

    private fun restartRow(cardTransformation : Card.() -> Card) {
        for (position in previouslyClickedCardsPositions) {
            board[position] = board[position].cardTransformation()
        }
        previouslyClickedCardsPositions.clear()
        cardsInRowMismatched = false
    }


    override fun getBoard(): Reaction.Running = Reaction.Running(ImmutableList(board))

    override fun onCardClicked(position: Int): Reaction {
        if (cardIsAlreadyOpen(position)) return Reaction.SameItemClicked

        if (cardsInRowMismatched) restartRow{ copy(isFaceUp = false) }

        if (previouslyClickedCardsPositions.size == difficulty.cardsInRow)
        { restartRow{ copy(isMatched = true) } }

        if (previouslyClickedCardsPositions.isNotEmpty() &&
            board[previouslyClickedCardsPositions.last()].identifier != board[position].identifier
        ) { cardsInRowMismatched = true }

        board[position] = board[position].copy(isFaceUp = true)
        previouslyClickedCardsPositions.add(position)

        if (isGameOver()) {
            restartRow{copy(isMatched = true)}
            return Reaction.Finished(board.toList(), mismatchedTimes)
        }

        return getBoard()
    }

}