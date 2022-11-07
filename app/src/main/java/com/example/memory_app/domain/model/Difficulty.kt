package com.example.memory_app.domain.model

enum class Difficulty(val NumberOfCards : Int, val cardsInRow : Int, val mismatchAllowed : Int){
    EASY(8, 2, 8),
    MEDIUM(12, 2, 12),
    HARD(15, 3, 15)
}