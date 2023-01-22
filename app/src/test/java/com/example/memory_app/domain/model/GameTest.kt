package com.example.memory_app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameTest {
    @Test(expected = IllegalArgumentException::class)
    fun `test board size should be multiple of cardsInRow`() {
        GameImpl(
            board = MutableList(8) { Card(it) },
            cardsInRow = 3,
            mismatchAllowed = 1
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test board cant be empty`() {
        GameImpl(
            board = mutableListOf(),
            cardsInRow = 3,
            mismatchAllowed = 1
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test mismatchAllowed more than 0`() {
        GameImpl(
            board = MutableList(8) { Card(it) },
            cardsInRow = 3,
            mismatchAllowed = -1
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test cardsInRow more than 0`() {
        GameImpl(
            board = MutableList(8) { Card(it) },
            cardsInRow = 0,
            mismatchAllowed = 1
        )
    }

    @Test
    fun `test game win`() {
        val board = mutableListOf (
            Card(0), Card(0),
            Card(1), Card(1)
        )
        with(GameImpl(board = board, cardsInRow = 2, mismatchAllowed = 1)) {
            assertTrue(onCardSelected(0) is Reaction.Running)
            assertTrue(onCardSelected(1) is Reaction.Running)
            assertTrue(onCardSelected(2) is Reaction.Running)
            assertTrue(onCardSelected(3) is Reaction.Win)
        }
    }

    @Test
    fun `test game loss`() {
        val board = mutableListOf (
            Card(0), Card(0),
            Card(1), Card(1)
        )
        val mismatches = 1

        with(GameImpl(board = board, cardsInRow = 2, mismatchAllowed = mismatches)) {
            assertTrue(onCardSelected(0) is Reaction.Running)
            assertTrue(onCardSelected(2) is Reaction.Loss)
        }
    }

    @Test
    fun `test same item selected`() {
        val board = mutableListOf (
            Card(0), Card(0),
            Card(1), Card(1)
        )
        with(GameImpl(board = board, cardsInRow = 2, mismatchAllowed = 1)) {
            assertTrue(onCardSelected(0) is Reaction.Running)
            assertTrue(onCardSelected(0) is Reaction.Nothing)
        }
    }

    @Test
    fun `test 3 cards in row game`() {
        val board = mutableListOf (
            Card(0), Card(0),
            Card(0), Card(1),
            Card(1), Card(1),
        )
        val mismatches = 2

        with(GameImpl(board = board, cardsInRow = 3, mismatchAllowed = mismatches)) {
            onCardSelected(0)
            onCardSelected(1)
            assertEquals(listOf(
                Card(0, isFaceUp = true),
                Card(0, isFaceUp = true),
                Card(0, isFaceUp = false),
                Card(1, isFaceUp = false),
                Card(1, isFaceUp = false),
                Card(1, isFaceUp = false)
            ), getBoard().toList())

            onCardSelected(3)
            assertEquals(1, getMismatchesLeft())
            assertEquals(listOf(
                Card(0, isFaceUp = true),
                Card(0, isFaceUp = true),
                Card(0, isFaceUp = false),
                Card(1, isFaceUp = true),
                Card(1, isFaceUp = false),
                Card(1, isFaceUp = false)
            ), getBoard().toList())

            onCardSelected(4)
            assertEquals(listOf(
                Card(0, isFaceUp = false),
                Card(0, isFaceUp = false),
                Card(0, isFaceUp = false),
                Card(1, isFaceUp = false),
                Card(1, isFaceUp = true),
                Card(1, isFaceUp = false),
            ), getBoard().toList())

            onCardSelected(3)
            onCardSelected(5)
            assertEquals(listOf(
                Card(0, isFaceUp = false),
                Card(0, isFaceUp = false),
                Card(0, isFaceUp = false),
                Card(1, isFaceUp = true),
                Card(1, isFaceUp = true),
                Card(1, isFaceUp = true),
            ), getBoard().toList())

            onCardSelected(0)
            assertEquals(listOf(
                Card(0, isFaceUp = true),
                Card(0, isFaceUp = false),
                Card(0, isFaceUp = false),
                Card(1, isFaceUp = true, isMatched = true),
                Card(1, isFaceUp = true, isMatched = true),
                Card(1, isFaceUp = true, isMatched = true),
            ), getBoard().toList())

            onCardSelected(1)
            assertTrue(onCardSelected(2) is Reaction.Win)
            assertEquals(
                board.map { card -> card.copy(isFaceUp = true, isMatched = true) },
                getBoard().toList())
        }
    }
}