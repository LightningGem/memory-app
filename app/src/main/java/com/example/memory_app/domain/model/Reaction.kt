package com.example.memory_app.domain.model

sealed class Reaction {
    object SameItemClicked : Reaction()
    data class Finished(val cards: List<Card>, val mismatchedTimes : Int) : Reaction()
    data class Running(val cards: List<Card>) : Reaction()
}