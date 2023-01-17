package com.example.memory_app.domain.model

import com.example.memory_app.domain.utils.ImmutableList

interface Game {
    fun onCardSelected(position: Int): Reaction
    fun getBoard(): Reaction.Running
}

class GameImpl(
    private val board: MutableList<Card>,
    private val cardsInRow: Int,
    private val mismatchAllowed : Int
) : Game {

    init { require(board.size % cardsInRow == 0) }

    private val previouslySelectedCardsPositions: MutableList<Int> = mutableListOf()

    private var mismatchesLeft = mismatchAllowed

    private var cardsInRowMismatched = false
        set(value) {
            if (value) {
                mismatchesLeft -= 1
            }
            field = value
        }

    private val loss : Boolean
        get() = mismatchesLeft == 0


    private fun isGameOver() = board.all { Card -> Card.isFaceUp } || loss

    private fun cardIsAlreadyOpen(position: Int): Boolean = board[position].isFaceUp

    private fun restartRow(cardTransformation : Card.() -> Card) {
        for (position in previouslySelectedCardsPositions) {
            board[position] = board[position].cardTransformation()
        }
        previouslySelectedCardsPositions.clear()
        cardsInRowMismatched = false
    }


    override fun getBoard(): Reaction.Running = Reaction.Running(ImmutableList(board), mismatchesLeft)

    override fun onCardSelected(position: Int): Reaction {
        if (cardIsAlreadyOpen(position) || loss) return Reaction.Nothing

        // previous cards clicked check
        if (cardsInRowMismatched) restartRow{ copy(isFaceUp = false) }

        if (previouslySelectedCardsPositions.size == cardsInRow)
        { restartRow{ copy(isMatched = true) } }

        if (previouslySelectedCardsPositions.isNotEmpty() &&
            board[previouslySelectedCardsPositions.last()].identifier != board[position].identifier
        ) { cardsInRowMismatched = true }

        // current card clicked save
        board[position] = board[position].copy(isFaceUp = true)
        previouslySelectedCardsPositions.add(position)

        // current card clicked check for game Finish or Loss
        if (isGameOver()) {
            if(loss) return Reaction.Loss(ImmutableList(board))

            restartRow{copy(isMatched = true)}
            return Reaction.Finished(ImmutableList(board), mismatchAllowed - mismatchesLeft)
        }

        return getBoard()
    }
}