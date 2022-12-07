package com.example.memory_app.domain.model

import javax.inject.Inject

interface BoardGenerator {
    fun generateListOfCards(difficulty : Difficulty, uniqueIds : List<Int>) : MutableList<Card>
}

class BoardGeneratorImpl @Inject constructor() : BoardGenerator {
    override fun generateListOfCards(
        difficulty: Difficulty,
        uniqueIds: List<Int>
    ): MutableList<Card> {

        require(uniqueIds.isNotEmpty())

        val numberOfUniqueIds = difficulty.NumberOfCards / difficulty.cardsInRow

        if(uniqueIds.size != numberOfUniqueIds) {
            val newUniqueIds = if (uniqueIds.size > numberOfUniqueIds)
                uniqueIds.shuffled().take(numberOfUniqueIds)
                    else buildList {
                        repeat(numberOfUniqueIds - uniqueIds.size) {
                        add(uniqueIds.random())
                    }
                        addAll(uniqueIds)
                }.shuffled()

            return generateListOfCards(difficulty,  newUniqueIds)
        }

        return buildList<Card> { uniqueIds.forEach { identifier ->
                repeat(difficulty.cardsInRow) {
                    add(Card(identifier))
                }
            }
        }.shuffled().toMutableList()
    }
}



