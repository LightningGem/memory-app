package com.example.memory_app.domain.model

data class Card (val identifier : Int,
                 val isMatched : Boolean = false,
                 val isFaceUp : Boolean = false)