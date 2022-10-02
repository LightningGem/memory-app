package com.example.memory_app.domain

enum class Difficulty(val NumberOfCards : Int, val cardsInRow : Int){
    EASY(6, 2),
    MEDIUM(12, 2),
    HARD(18, 3)
}