package com.example.memory_app.domain.model

import com.example.memory_app.utils.ImmutableList

interface Game {
    fun onCardSelected(position: Int): Reaction
    fun getBoard(): Reaction.Running
}

class GameImpl(
    private val board: MutableList<Card>,
    private val difficulty: Difficulty
) : Game {

    private val previouslySelectedCardsPositions: MutableList<Int> = mutableListOf()

    private var mismatchedTimes : Int = 0

    private var cardsInRowMismatched = false
        set(value) {
            if (value) mismatchedTimes += 1
            field = value
        }

    private fun isGameOver() = board.all { Card -> Card.isFaceUp }

    private fun cardIsAlreadyOpen(position: Int): Boolean = board[position].isFaceUp

    private fun restartRow(cardTransformation : Card.() -> Card) {
        for (position in previouslySelectedCardsPositions) {
            board[position] = board[position].cardTransformation()
        }
        previouslySelectedCardsPositions.clear()
        cardsInRowMismatched = false
    }


    override fun getBoard(): Reaction.Running = Reaction.Running(ImmutableList(board))

    override fun onCardSelected(position: Int): Reaction {
        if (cardIsAlreadyOpen(position)) return Reaction.OpenItemSelected

        if (cardsInRowMismatched) restartRow{ copy(isFaceUp = false) }

        if (previouslySelectedCardsPositions.size == difficulty.cardsInRow)
        { restartRow{ copy(isMatched = true) } }

        if (previouslySelectedCardsPositions.isNotEmpty() &&
            board[previouslySelectedCardsPositions.last()].identifier != board[position].identifier
        ) { cardsInRowMismatched = true }

        board[position] = board[position].copy(isFaceUp = true)
        previouslySelectedCardsPositions.add(position)

        if (isGameOver()) {
            restartRow{copy(isMatched = true)}
            return Reaction.Finished(ImmutableList(board), mismatchedTimes)
        }

        return getBoard()
    }
}