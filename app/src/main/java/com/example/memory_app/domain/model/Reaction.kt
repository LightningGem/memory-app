package com.example.memory_app.domain.model

sealed class Reaction {
    object Nothing : Reaction()
    data class Running(val cards: List<Card>, val mismatchesLeft : Int) : Reaction()
    data class Loss(val cards: List<Card>) : Reaction()
    data class Finished(val cards: List<Card>, val mismatchedTimes : Int) : Reaction()
}